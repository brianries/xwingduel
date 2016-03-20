package network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Brian on 3/19/2016.
 * Borrowing 90% of this code from http://rox-xmlrpc.sourceforge.net/niotut/#Introduction
 */
public abstract class NioService implements Runnable {

    protected static final Logger log = LogManager.getLogger(NioServer.class);

    // Keeps track of message size we can read whole messages
    protected ByteBuffer headerBuffer = ByteBuffer.allocate(4);

    // A list of ChangeRequest instances
    protected List changeRequests = new LinkedList();

    // Maps a SocketChannel to a list of ByteBuffer instances
    protected Map pendingData = new HashMap();

    // The selector we'll be monitoring
    protected Selector selector;

    protected ArrayList<SocketChannel> connections = new ArrayList<>();

    protected IncomingDataProcessor incomingDataProcessor;
    public interface IncomingDataProcessor {
        void processData(SocketChannel sourceChannel, byte[] data, int count);
    }

    public NioService(IncomingDataProcessor incomingDataProcessor) {
        this.incomingDataProcessor = incomingDataProcessor;
    }

    private AtomicBoolean shutdown = new AtomicBoolean(false);

    public void run() {
        log.debug("Starting NIO run thread...");
        while (!shutdown.get()) {
            try {
                // Process any pending changes
                synchronized(this.changeRequests) {
                    Iterator changes = this.changeRequests.iterator();
                    while (changes.hasNext()) {
                        ChangeRequest change = (ChangeRequest) changes.next();
                        switch(change.type) {
                            case ChangeRequest.CHANGEOPS:
                                SelectionKey key = change.socket.keyFor(this.selector);
                                if (key != null) {
                                    key.interestOps(change.ops);
                                }
                            case ChangeRequest.REGISTER:
                                if (change.socket.isOpen()) {
                                    change.socket.register(this.selector, change.ops);
                                }
                                break;
                        }
                    }
                    this.changeRequests.clear();
                }

                // Wait for an event one of the registered channels
                this.selector.select();

                // Iterate over the set of keys for which events are available
                Iterator selectedKeys = this.selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = (SelectionKey) selectedKeys.next();
                    selectedKeys.remove();

                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isConnectable()) {
                        this.finishConnection(key);
                    }
                    if (key.isAcceptable()) {
                        this.accept(key);
                    }
                    else if (key.isReadable()) {
                        this.read(key);
                    }
                    else if (key.isWritable()) {
                        this.write(key);
                    }
                }
            } catch (Exception e) {
                log.error("Exception in main NioServer loop. Stack trace:", e);
            }
        }

        for (SocketChannel connection : connections) {
            try {
                connection.close();
            } catch (IOException e) {
                log.error("Error closing connection. Stack trace = ", e);
            }
        }
        connections.clear();
    }

    private void finishConnection(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        // Finish the connection. If the connection operation failed
        // this will raise an IOException.
        try {
            socketChannel.finishConnect();
            connections.add(socketChannel);
            log.debug("Successfully connected to " + socketChannel.getRemoteAddress().toString());
        } catch (IOException e) {
            log.error("Failed to finish connection. Stack trace: ", e);
            // Cancel the channel's registration with our selector
            key.cancel();
        }
    }

    public void send(SocketChannel socketChannel, byte[] data) {
        synchronized (this.changeRequests) {
            // Indicate we want the interest ops set changed
            this.changeRequests.add(new ChangeRequest(socketChannel, ChangeRequest.CHANGEOPS, SelectionKey.OP_WRITE));

            // And queue the data we want written
            synchronized (this.pendingData) {
                List queue = (List) this.pendingData.get(socketChannel);
                if (queue == null) {
                    queue = new ArrayList();
                    this.pendingData.put(socketChannel, queue);
                }
                queue.add(ByteBuffer.wrap(data));
            }
        }

        // Finally, wake up our selecting thread so it can make the required changes
        this.selector.wakeup();
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        // Attempt to read off the channel
        int numRead = 0;
        ByteBuffer payload = null;
        try {
            headerBuffer.clear();
            numRead = socketChannel.read(headerBuffer);
            if (numRead != -1) {
                headerBuffer.flip();
                int length = headerBuffer.getInt();
                payload = ByteBuffer.allocate(length);
                numRead = socketChannel.read(payload);
            }
        } catch (IOException e) {
            // The remote forcibly closed the connection, cancel
            // the selection key and close the channel.
            key.cancel();
            socketChannel.close();
            return;
        }

        if (numRead == -1) {
            log.debug("Connection dropped from " + socketChannel.getRemoteAddress().toString());
            // Remote entity shut the socket down cleanly. Do the
            // same from our end and cancel the channel.
            key.channel().close();
            key.cancel();
            connections.remove(socketChannel);
            return;
        }

        // Hand the data off to our worker thread
        this.incomingDataProcessor.processData(socketChannel, payload.array(), numRead);
    }


    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        synchronized (this.pendingData) {
            List queue = (List) this.pendingData.get(socketChannel);

            if (queue != null) {
                // Write until there's not more data ...
                while (!queue.isEmpty()) {
                    ByteBuffer buf = (ByteBuffer) queue.get(0);
                    headerBuffer.clear();
                    headerBuffer.putInt(buf.limit());
                    headerBuffer.flip();
                    socketChannel.write(headerBuffer);
                    socketChannel.write(buf);
                    if (headerBuffer.remaining() > 0 || buf.remaining() > 0) {
                        // ... or the socket's buffer fills up
                        break;
                    }
                    queue.remove(0);
                }

                if (queue.isEmpty()) {
                    // We wrote away all data, so we're no longer interested
                    // in writing on this socket. Switch back to waiting for
                    // data.
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        log.debug("Handling new connection...");
        // For an accept to be pending the channel must be a server socket channel.
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

        // Accept the connection and make it non-blocking
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        log.debug("Accepted new connection from " + socketChannel.getRemoteAddress().toString());
        connections.add(socketChannel);

        // Register the new SocketChannel with our Selector, indicating
        // we'd like to be notified when there's data waiting to be read
        socketChannel.register(this.selector, SelectionKey.OP_READ);
        log.debug("Connection complete ");
    }

    public void shutdown() {
        shutdown.set(true);
        this.selector.wakeup();
    }

}
