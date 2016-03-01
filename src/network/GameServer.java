package network;


import network.playercommand.AddShipCommand;
import network.update.UpdateMessage;
import network.update.UpdateCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import state.ServerBoardState;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Brian on 2/8/2016.
 */
public class GameServer implements NioServer.IncomingDataProcessor, ServerBoardState.UpdateListener {

    private static final Logger log = LogManager.getLogger(GameServer.class);

    public static final int SERVER_PORT = 5000;

    private NioServer nioServer;
    private ServerBoardState serverBoardState;


    private LinkedList<ServerData> queue = new LinkedList<>();
    private AtomicBoolean shutdown = new AtomicBoolean();

    private Thread handleDataThread;
    private Thread nioServerThread;

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
                    log.error("Error in handle data. Stack trace: ", e);
                }
            }
        };
        this.handleDataThread.start();
        this.serverBoardState.addUpdateListener(this);

        this.nioServerThread = new Thread(nioServer);
        this.nioServerThread.start();

        try {
            this.handleDataThread.join();
        } catch (InterruptedException ignored) { }
    }


    @Override
    public void processData(SocketChannel socketChannel, byte[] data, int count) {
        synchronized(queue) {
            log.debug("processData called...");
            ServerData serverData = new ServerData();
            serverData.channel = socketChannel;
            serverData.data = data.clone();
            queue.add(serverData);
            queue.notify();
        }
    }

    public void handleData() throws IOException, ClassNotFoundException {
        while(!shutdown.get()) {
            // Wait for data to become available
            ServerData serverData;
            synchronized(queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException ignored) {}
                }
                serverData = queue.remove(0);
            }

            SerializationUtil.PlayerPayLoad payLoad = SerializationUtil.deserializePlayerPayLoad(serverData.data);
            log.debug("Handling server data (" + payLoad.command + ")");
            switch (payLoad.command) {

                case ADD_SHIP:
                    AddShipCommand addShipCommand = (AddShipCommand)payLoad.object;
                    this.serverBoardState.addShip(addShipCommand.getPlayer(), addShipCommand.getFaction(), addShipCommand.getShip(), addShipCommand.getPilot());
                    //this.handleUpdate(new UpdateMessage("State Update Response!"));
                    break;

                case ROLL_DICE:
                    byte[] data = SerializationUtil.serialize(UpdateCommand.UPDATE_MESSAGE, new UpdateMessage("Roll Dice response!"));
                    nioServer.send(serverData.channel, data);
                    break;
            }
        }
    }


    @Override
    public void handleUpdate(UpdateMessage update) {
        byte[] data;
        try {
            data = SerializationUtil.serialize(UpdateCommand.UPDATE_MESSAGE, update);
        } catch (IOException e) {
            log.error("Failure to serialize update " + update + " Stack trace: ", e);
            return;
        }
        nioServer.sendAll(data);
    }

    public void shutdown() {
        this.shutdown.set(true);
        this.queue.notifyAll();
    }


    public static void main(String[] args) {
        try {
            log.debug("Creating game server...");
            GameServer gameServer = new GameServer();
        } catch (Exception e) {
            log.error("Error: Stack trace: ", e);
        }
    }
}
