package network;

import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Brian on 2/8/2016.
 */
public class EchoWorker implements Runnable {
    private List queue = new LinkedList();

    public static class ServerDataEvent {
        public NioServer server;
        public SocketChannel socket;
        public byte[] data;

        public ServerDataEvent(NioServer server, SocketChannel socket, byte[] data) {
            this.server = server;
            this.socket = socket;
            this.data = data;
        }
    }

    public void processData(NioServer server, SocketChannel channel, byte[] data, int count) {
        try {
            SerializationUtil.PayLoad payLoad = SerializationUtil.deserialize(data);
            byte[] dataCopy = SerializationUtil.serialize(payLoad.command, payLoad.object);
            synchronized(queue) {
                queue.add(new ServerDataEvent(server, channel, dataCopy));
                queue.notify();
            }
            System.out.println();
        } catch (Exception ignored) { }
    }

    public void run() {
        ServerDataEvent dataEvent;

        while(true) {
            // Wait for data to become available
            synchronized(queue) {
                while(queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                    }
                }
                dataEvent = (ServerDataEvent) queue.remove(0);
            }

            // Return to sender
            dataEvent.server.send(dataEvent.socket, dataEvent.data);
        }
    }
}
