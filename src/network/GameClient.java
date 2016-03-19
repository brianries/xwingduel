package network;

import base.Faction;
import base.Player;
import expansions.core.pilots.AcademyPilot;
import expansions.core.ships.TieFighter;
import network.message.*;
import network.message.player.command.AddShipCommand;
import network.message.player.command.RollDiceCommand;
import network.message.server.ServerResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;


/**
 * Created by Brian on 2/8/2016.
 */
public class GameClient {

    private static final Logger log = LogManager.getLogger(GameClient.class);

    private NioClient client;
    private Thread clientThread;

    public GameClient() {
        try {
            InetAddress address = InetAddress.getByName("localhost"); // fix later
            client = new NioClient(address, GameServer.SERVER_PORT);
        }
        catch (Exception e) {
            log.error("Exception creating Game client! Stack trace: ", e);
        }

        clientThread = new Thread(client);
        clientThread.setDaemon(true);
        clientThread.start();
    }

    public static class SerializedResponseHandler implements NioClient.ResponseHandler {
        private Message message = null;
        public synchronized boolean handleResponse(byte[] response) {
            try {
                this.message = MessageSerializationUtil.deserialize(response);
                this.notify();
                return true;
            }
            catch (Exception e) {
                log.error("Error serializing response! Stack trace:", e);
            }

            return false;
        }

        public synchronized void waitForResponse() {
            while(message == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) { }
            }
            log.debug("Message (" + message.getCommandOrResponse().toString() + ") received");

            switch (message.getCommandOrResponse()) {
                case COMMAND:
                    break;
                case RESPONSE:
                    handleServerResponse((ServerResponse)message);
                    break;
            }
        }

        private void handleServerResponse(ServerResponse response) {
            switch (response.getMessageType()) {
                case UPDATE_MESSAGE:
                    log.info("Received server update message: " + response.getMessageType().toString());
                    break;
            }
        }
    }


    public void sendCommand(Message message) throws IOException {
        byte[] bytes = MessageSerializationUtil.serialize(message);
        SerializedResponseHandler handler = new SerializedResponseHandler();
        client.send(bytes, handler);
        handler.waitForResponse();
    }

    public void shutdown() throws IOException {
        client.shutdown();
    }


    public static void main(String[] args) {
        try {
            log.debug("Creating game client...");
            GameClient client = new GameClient();
            client.sendCommand(new AddShipCommand(Player.PLAYER_ONE, Faction.GALACTIC_EMPIRE, new TieFighter(), new AcademyPilot()));
            client.sendCommand(new RollDiceCommand(2));
            client.shutdown();
        } catch (Exception e) {
            log.error("Error: Stack trace: ", e);
        }
    }
}
