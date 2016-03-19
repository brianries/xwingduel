package network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;


/**
 * Created by Brian on 2/8/2016.
 */
public class NioServer extends NioService {

    // The host:port combination to listen on
    private InetAddress hostAddress;
    private int port;

    // The channel on which we'll accept connections
    private ServerSocketChannel serverChannel;

    public NioServer(InetAddress hostAddress, int port, IncomingDataProcessor incomingDataProcessor) throws IOException {
        super(incomingDataProcessor);
        this.hostAddress = hostAddress;
        this.port = port;
        this.selector = this.initSelector();
    }

    private Selector initSelector() throws IOException {
        // Create a new selector
        Selector socketSelector = SelectorProvider.provider().openSelector();

        // Create a new non-blocking server socket channel
        this.serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        // Bind the server socket to the specified address and port
        InetSocketAddress isa = new InetSocketAddress(this.hostAddress, this.port);
        serverChannel.socket().bind(isa);

        // Register the server socket channel, indicating an interest in
        // accepting new connections
        serverChannel.register(socketSelector, SelectionKey.OP_ACCEPT);

        return socketSelector;
    }

    public void sendAll(byte[] data) {
        for (SocketChannel channel : connections) {
            send(channel, data);
        }
    }
}
