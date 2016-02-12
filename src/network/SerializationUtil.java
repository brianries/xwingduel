package network;

import java.io.*;

/**
 * Created by Brian on 2/11/2016.
 */
public class SerializationUtil {

    public static byte[] serialize(Command command, Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream((bos))) {
            out.writeObject(command);
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    public static class PayLoad {
        public Command command;
        public Object object;
    }

    public static PayLoad deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        // try with resources -- auto closes these objects when finished
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
            PayLoad payLoad = new PayLoad();
            payLoad.command = (Command)in.readObject();
            payLoad.object = in.readObject();
            return payLoad;
        }
    }
}
