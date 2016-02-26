package network;


import network.playercommand.AddShipCommand;
import network.playercommand.PlayerCommand;
import state.ServerBoardState;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * Created by Brian on 2/8/2016.
 */
public class GameServer implements NioServer.IncomingDataProcessor, ServerBoardState.UpdateListener {
    public static final int SERVER_PORT = 5000;

    private NioServer nioServer;
    private ServerBoardState serverBoardState;
    private volatile boolean shutdown = false;

    private LinkedList<ServerData> queue = new LinkedList<>();

    private Thread handleDataThread;

    private static class ServerData {
        public SocketChannel channel;
        public byte[] data;
    }

    public GameServer() throws IOException {
        this.serverBoardState = new ServerBoardState();
        this.nioServer = new NioServer(null, SERVER_PORT, this);
        this.handleDataThread = new Thread() {
            @Override
            public void run() {
                try {
                    handleData();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.handleDataThread.start();
        this.serverBoardState.addUpdateListener(this);
    }


    @Override
    public void processData(SocketChannel socketChannel, byte[] data, int count) {
        synchronized(queue) {
            ServerData serverData = new ServerData();
            serverData.channel = socketChannel;
            serverData.data = data.clone();
            queue.add(serverData);
            queue.notify();
        }
    }

    public void handleData() throws IOException, ClassNotFoundException {
        while(!shutdown) {
            // Wait for data to become available
            ServerData serverData;
            synchronized(queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                    }
                }
                serverData = queue.remove(0);
            }

            SerializationUtil.PayLoad payLoad = SerializationUtil.deserialize(serverData.data);
            if (payLoad.command == PlayerCommand.ADD_SHIP) {
                AddShipCommand addShipCommand = (AddShipCommand)payLoad.object;
                this.serverBoardState.addShip(addShipCommand.getPlayer(), addShipCommand.getFaction(), addShipCommand.getShip(), addShipCommand.getPilot());
            }

            //byte[] dataCopy = SerializationUtil.serialize(payLoad.command, payLoad.object);

            // Return to sender
            //dataEvent.server.send(dataEvent.socket, dataEvent.data);
        }
    }


    @Override
    public void handleUpdate(ServerBoardState.Update update) {
        //nioServer.send();
    }
}
