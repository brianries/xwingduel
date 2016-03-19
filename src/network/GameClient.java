package network;

import base.*;
import expansions.core.pilots.*;
import expansions.core.ships.TieFighter;
import expansions.core.ships.XWing;
import expansions.core.upgrades.astromech.R2D2;
import network.message.*;
import network.message.player.PlayerCommand;
import network.message.player.command.AddSquadronCommand;
import network.message.player.command.PlaceShipCommand;
import network.message.player.command.RollDiceCommand;
import network.message.server.ServerCommand;
import network.message.server.ServerResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rendering.obstacles.ObstacleType;

import java.io.*;
import java.net.InetAddress;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by Brian on 2/8/2016.
 */
public class GameClient implements NioClient.IncomingDataProcessor {

    private static final Logger log = LogManager.getLogger(GameClient.class);

    private LinkedList<NioData> queue = new LinkedList<>();
    private volatile boolean isRunning = true;

    private NioClient client;

    private Thread nioClientThread;
    private Thread handleDataThread;

    public GameClient() {
        try {
            InetAddress address = InetAddress.getByName("localhost"); // fix later
            client = new NioClient(address, GameServer.SERVER_PORT, this);
        }
        catch (Exception e) {
            log.error("Exception creating Game client! Stack trace: ", e);
        }

        this.nioClientThread = new Thread(client);
        this.nioClientThread.setDaemon(true);
        this.nioClientThread.start();

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
    }

    @Override
    public void processData(SocketChannel socketChannel, byte[] data, int count) {
        synchronized(queue) {
            NioData serverData = new NioData();
            serverData.channel = socketChannel;
            serverData.data = data.clone();
            queue.add(serverData);
            queue.notify();
        }
    }

    public void handleData() throws IOException, ClassNotFoundException {
        while(isRunning) {
            // Wait for data to become available
            NioData serverData;
            synchronized(queue) {
                while (queue.isEmpty() && isRunning) {
                    try {
                        queue.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
                if (isRunning) {
                    serverData = queue.remove(0);
                }
                else {
                    return;
                }
            }

            Message message = MessageSerializationUtil.deserialize(serverData.data);
            log.debug("Handling server data (" + message.getCommandOrResponse().toString() + ")");
            switch (message.getCommandOrResponse()) {
                case COMMAND:
                    handleServerCommand(serverData.channel, (ServerCommand) message);
                    break;
                case RESPONSE:
                    handleServerResponse(serverData.channel, (ServerResponse) message);
                    break;
                default:
                    // error
                    break;
            }
        }
    }

    private void handleServerCommand(SocketChannel channel, ServerCommand serverCommand) {
        log.debug("Handling server command data (" + serverCommand.getMessageType().toString() + ")");

    }

    private void handleServerResponse(SocketChannel channel, ServerResponse serverResponse) {
        log.debug("Handling server response data (" + serverResponse.getMessageType().toString() + ")");
    }

    public void sendCommand(PlayerCommand command) throws IOException {
        log.debug("Sending player command (" + command.getMessageType() + ")");
        byte[] bytes = MessageSerializationUtil.serialize(command);
        client.send(bytes);
    }

    public void shutdown() throws IOException {
        client.shutdown();
        try {
            nioClientThread.join();
        } catch (InterruptedException ignored) { }

        synchronized (queue) {
            log.debug("Shutting down...");
            this.isRunning = false;
            this.queue.notifyAll();
        }
    }


    public static void main(String[] args) {
        try {
            log.debug("Creating game client...");
            GameClient client = new GameClient();

            log.debug("Creating canned rebel squadron");

            UnitSubmission rebelUnit1 = new UnitSubmission(new XWing(), new RookiePilot());
            UnitSubmission rebelUnit2 = new UnitSubmission(new XWing(), new BiggsDarklighter());
            UnitSubmission rebelUnit3 = new UnitSubmission(new XWing(), new LukeSkywalker(), new R2D2());

            ObstacleType selectedObstacles[] = {
                    ObstacleType.ASTEROID_BASE_CORE_0,
                    ObstacleType.ASTEROID_BASE_CORE_1,
                    ObstacleType.ASTEROID_BASE_CORE_2,
            };

            client.sendCommand(new AddSquadronCommand(Faction.REBEL_ALLIANCE, selectedObstacles, rebelUnit1, rebelUnit2, rebelUnit3));
            //client.sendCommand(new PlaceShipCommand(Player.PLAYER_ONE, Faction.GALACTIC_EMPIRE, new TieFighter(), new AcademyPilot()));
            client.sendCommand(new RollDiceCommand(2));
            Thread.sleep(3_000);
            client.shutdown();
        } catch (Exception e) {
            log.error("Error: Stack trace: ", e);
        }
    }
}
