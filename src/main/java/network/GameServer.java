package network;


import base.Player;
import network.message.*;
import network.message.player.PlayerCommand;
import network.message.player.command.AddSquadronCommand;
import network.message.player.command.PlaceShipCommand;
import network.message.player.command.RollDiceCommand;
import network.message.server.ServerResponse;
import network.message.server.command.BoardStateUpdateCommand;
import network.message.server.response.AddSquadronResponse;
import network.message.server.response.PlaceShipResponse;
import network.message.server.response.RollDiceResponse;
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

    private LinkedList<NioData> queue = new LinkedList<>();
    private AtomicBoolean shutdown = new AtomicBoolean();

    private Thread handleDataThread;
    private Thread nioServerThread;

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
            NioData serverData = new NioData();
            serverData.channel = socketChannel;
            serverData.data = data.clone();
            queue.add(serverData);
            queue.notify();
        }
    }

    public void handleData() throws IOException, ClassNotFoundException {
        while(!shutdown.get()) {
            // Wait for data to become available
            NioData serverData;
            synchronized(queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException ignored) {}
                }
                serverData = queue.remove(0);
            }

            Message message = MessageSerializationUtil.deserialize(serverData.data);
            log.debug("Handling server data (" + message.getCommandOrResponse().toString() + ")");
            switch (message.getCommandOrResponse()) {
                case COMMAND:
                    handlePlayerCommand(serverData.channel, (PlayerCommand)message);
                    break;
                case RESPONSE:
                    break;
                default:
                    // error
                    break;

            }
        }
    }

    // TODO work on the player registration
    private Player getPlayerFromChannel(SocketChannel channel) {
        return Player.PLAYER_ONE;
    }

    private void handlePlayerCommand(SocketChannel channel, PlayerCommand playerCommand) {
        log.debug("Handling player command data (" + playerCommand.getMessageType().toString() + ")");
        switch (playerCommand.getMessageType()) {
            case ADD_SQUADRON:
                AddSquadronCommand addSquadronCommand = (AddSquadronCommand) playerCommand;
                sendResponse(channel, new AddSquadronResponse());
                this.serverBoardState.addSquadron(getPlayerFromChannel(channel), addSquadronCommand.getFaction(), addSquadronCommand.getUnitSubmissions());
                break;

            case PLACE_SHIP:
                PlaceShipCommand addShipCommand = (PlaceShipCommand) playerCommand;
                sendResponse(channel, new PlaceShipResponse());
                break;

            case ROLL_DICE:
                RollDiceCommand rollDiceCommand = (RollDiceCommand) playerCommand;
                // roll some dice
                sendResponse(channel, new RollDiceResponse());
                break;
        }
    }

    public void sendResponse(SocketChannel channel, ServerResponse response) {
        log.debug("Sending server response (" + response.getMessageType().toString() + ")");
        byte[] data;
        try {
            data = MessageSerializationUtil.serialize(response);
        } catch (IOException e) {
            log.error("Failure to serialize server command " + response + " Stack trace: ", e);
            return;
        }
        nioServer.send(channel, data);
    }


    @Override
    public void handleUpdate(BoardStateUpdateCommand update) {
        byte[] data;
        try {
            data = MessageSerializationUtil.serialize(update);
        } catch (IOException e) {
            log.error("Failure to serialize server command " + update + " Stack trace: ", e);
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
            gameServer.shutdown();
        } catch (Exception e) {
            log.error("Error: Stack trace: ", e);
        }
    }
}
