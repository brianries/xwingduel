package network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;

/**
 * Created by Brian on 2/8/2016.
 * Borrowing 99% of this code from http://rox-xmlrpc.sourceforge.net/niotut/#Introduction
 */
public class NioClient extends NioService {

    // The host:port combination to listen on
    private InetAddress hostAddress;
    private int port;

    private SocketChannel socket;

    public NioClient(InetAddress hostAddress, int port, IncomingDataProcessor incomingDataProcessor) throws IOException {
        super(incomingDataProcessor);
        this.hostAddress = hostAddress;
        this.port = port;
        this.selector = initSelector();
        this.socket = initiateConnection();
    }

    private Selector initSelector() throws IOException {
        return SelectorProvider.provider().openSelector();
    }

    private SocketChannel initiateConnection() throws IOException {
        // Create a non-blocking socket channel
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        log.debug("Attempting connection to " + this.hostAddress + ":" + this.port);
        // Kick off connection establishment
        socketChannel.connect(new InetSocketAddress(this.hostAddress, this.port));

        // Queue a channel registration since the caller is not the
        // selecting thread. As part of the registration we'll register
        // an interest in connection events. These are raised when a channel
        // is ready to complete connection establishment.
        synchronized(this.changeRequests) {
            this.changeRequests.add(new ChangeRequest(socketChannel, ChangeRequest.REGISTER, SelectionKey.OP_CONNECT));
        }

        return socketChannel;
    }

    public void send(byte[] data) {
        this.send(socket, data);
    }
}
