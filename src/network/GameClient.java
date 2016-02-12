package network;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Created by Brian on 2/8/2016.
 */
public class GameClient {

    public static int port = 9090;

    private NioClient client;
    private Thread clientThread;

    public GameClient() {
        try {
            InetAddress address = InetAddress.getByName("localhost"); // fix later
            client = new NioClient(address, port);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        clientThread = new Thread(client);
        clientThread.setDaemon(true);
        clientThread.start();
    }

    public static class SerializedResponseHandler implements NioClient.ResponseHandler {
        SerializationUtil.PayLoad payLoad = null;

        public synchronized boolean handleResponse(byte[] response) {
            try {
                payLoad = SerializationUtil.deserialize(response);
                this.notify();
                return true;
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        public synchronized void waitForResponse() {
            while(this.payLoad == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                }
            }

            System.out.println(payLoad.command.toString());
        }
    }


    public void sendCommand(Command command, Object payload) throws IOException {
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
            GameClient client = new GameClient();
            client.sendCommand(Command.ROLL_DICE, new RollDiceCommand(2));
            client.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
