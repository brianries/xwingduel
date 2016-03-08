package network;

import network.playercommand.PlayerCommand;
import network.servercommand.ServerCommand;

import java.io.*;

/**
 * Created by Brian on 2/11/2016.
 */
public class SerializationUtil {

    public static class PlayerPayLoad {
        public PlayerCommand command;
        public Object object;
    }

    public static class UpdatePayLoad {
        public ServerCommand command;
        public Object object;
    }

    public static byte[] serialize(Object command, Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream((bos))) {
            out.writeObject(command);
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    public static PlayerPayLoad deserializePlayerPayLoad(byte[] bytes) throws IOException, ClassNotFoundException {
        // try with resources -- auto closes these objects when finished
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
            PlayerPayLoad payLoad = new PlayerPayLoad();
            payLoad.command = (PlayerCommand)in.readObject();
            payLoad.object = in.readObject();
            return payLoad;
        }
    }

    public static UpdatePayLoad deserializeUpdatePayLoad(byte[] bytes) throws IOException, ClassNotFoundException {
        // try with resources -- auto closes these objects when finished
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
            UpdatePayLoad payLoad = new UpdatePayLoad();
            payLoad.command = (ServerCommand)in.readObject();
            payLoad.object = in.readObject();
            return payLoad;
        }
    }
}
