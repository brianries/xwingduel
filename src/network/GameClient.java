package network;

import base.Faction;
import base.Player;
import expansions.core.pilots.AcademyPilot;
import expansions.core.ships.TieFighter;
import network.playercommand.AddShipCommand;
import network.playercommand.PlayerCommand;
import network.playercommand.RollDice;
import network.update.UpdateMessage;
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
        SerializationUtil.UpdatePayLoad payLoad = null;

        public synchronized boolean handleResponse(byte[] response) {
            try {
                payLoad = SerializationUtil.deserializeUpdatePayLoad(response);
                this.notify();
                return true;
            }
            catch (Exception e) {
                log.error("Error serializing response! Stack trace:", e);
            }

            return false;
        }

        public synchronized void waitForResponse() {
            while(this.payLoad == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) { }
            }
            log.debug("Payload (" + payLoad.command.toString() + ") received");

            switch (payLoad.command) {
                case UPDATE_MESSAGE:
                    log.info("Received update message: " + payLoad.object);
                    break;
            }
        }
    }


    public void sendCommand(PlayerCommand command, Object payload) throws IOException {
        byte[] bytes = SerializationUtil.serialize(command, payload);
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
            client.sendCommand(PlayerCommand.ADD_SHIP, new AddShipCommand(Player.PLAYER_ONE, Faction.GALACTIC_EMPIRE, new TieFighter(), new AcademyPilot()));
            client.sendCommand(PlayerCommand.ROLL_DICE, new RollDice(2));
            client.shutdown();
        } catch (Exception e) {
            log.error("Error: Stack trace: ", e);
        }
    }
}
