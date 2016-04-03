package network;

import base.*;
import expansions.core.pilots.*;
import expansions.core.ships.XWing;
import expansions.core.upgrades.astromech.R2D2;
import network.message.*;
import network.message.PlayerCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rendering.obstacles.ObstacleType;

import java.io.*;
import java.net.InetAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * Created by Brian on 2/8/2016.
 */
public class GameClient implements NioClient.IncomingDataProcessor {

    private static final Logger log = LogManager.getLogger(GameClient.class);

    private LinkedList<NioData> queue = new LinkedList<>();
    private volatile boolean isRunning = true;

    private NioClient client;

    private int clientId;

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

            ProtoMessage.Message message = ProtoMessage.Message.parseFrom(serverData.data);
            log.debug("Handling server data (" + message.getSubMessageCase().toString() + ")");
            switch (message.getSubMessageCase()) {
                case SERVERCOMMAND:
                    handleServerCommand(serverData.channel, message.getServerCommand());
                    break;
                case SERVERRESPONSE:
                    handleServerResponse(serverData.channel, message.getServerResponse());
                    break;
                default:
                    // error
                    break;
            }
        }
    }

    private void handleServerCommand(SocketChannel channel, ServerCommands.BaseCommand serverCommand) {
        log.debug("Handling server command data (" + serverCommand.getCommandCase().toString() + ")");
        switch (serverCommand.getCommandCase()) {
            case UPDATEBOARDSTATE:
                ServerCommands.UpdateBoardState updateBoardState = serverCommand.getUpdateBoardState();
                //sendResponse(channel, );
                //this.serverBoardState.addSquadron(getPlayerFromChannel(channel), addSquadronCommand.getFaction(), addSquadronCommand.getUnitSubmissions());
                break;
        }
    }

    private void handleServerResponse(SocketChannel channel, ServerResponses.BaseResponse serverResponse) {
        log.debug("Handling server command data (" + serverResponse.getResponseCase().toString() + ")");
        switch (serverResponse.getResponseCase()) {
            case LOGIN:
                break;
            case COMMAND:
                break;
        }
    }

    private void handleServerUpdateBoardState(SocketChannel channel, ServerCommands.UpdateBoardState updateBoardState) {
        log.debug("Handling server update board state");
    }


    public void sendCommand(PlayerCommands.BaseCommand command) throws IOException {
        log.debug("Sending player command (" + command.getCommandCase().toString() + ")");

        ProtoMessage.Message message = ProtoMessage.Message.newBuilder()
                .setPlayerCommand(command)
                .build();

        client.send(message.toByteArray());
    }

    public void sendResponse(PlayerResponses.BaseResponse response) throws IOException {
        log.debug("Sending player response (" + response.getResponseCase().toString() + ")");

        ProtoMessage.Message message = ProtoMessage.Message.newBuilder()
                .setPlayerResponse(response)
                .build();

        client.send(message.toByteArray());
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

            //UnitSubmission rebelUnit1 = new UnitSubmission(new XWing(), new RookiePilot());
            ///UnitSubmission rebelUnit2 = new UnitSubmission(new XWing(), new BiggsDarklighter());
            //UnitSubmission rebelUnit3 = new UnitSubmission(new XWing(), new LukeSkywalker(), new R2D2());

            ArrayList<PlayerCommands.UnitSubmission> unitSubmissions = new ArrayList<>();
            unitSubmissions.add(PlayerCommands.UnitSubmission.newBuilder().build());

            ArrayList<Integer> selectedObstacles = new ArrayList<>(Arrays.asList(
                    ObstacleType.ASTEROID_BASE_CORE_0.ordinal(),
                    ObstacleType.ASTEROID_BASE_CORE_1.ordinal(),
                    ObstacleType.ASTEROID_BASE_CORE_2.ordinal()
            ));

            PlayerCommands.AddSquadron addSquadron = PlayerCommands.AddSquadron.newBuilder()
                    .setFaction(0)
                    .addAllSelectedObstacleTypes(selectedObstacles)
                    .addAllUnits(unitSubmissions)
                    .build();


            PlayerCommands.BaseCommand baseCommand = PlayerCommands.BaseCommand.newBuilder()
                    .setAddSquadron(addSquadron)
                    .build();

            client.sendCommand(baseCommand);

          //  client.sendCommand(new AddSquadronCommand(Faction.REBEL_ALLIANCE, selectedObstacles, rebelUnit1, rebelUnit2, rebelUnit3));
            //client.sendCommand(new PlaceShipCommand(Player.PLAYER_ONE, Faction.GALACTIC_EMPIRE, new TieFighter(), new AcademyPilot()));
            //client.sendCommand(new RollDiceCommand(2));
            Thread.sleep(3_000);
            client.shutdown();
        } catch (Exception e) {
            log.error("Error: Stack trace: ", e);
        }
    }
}
