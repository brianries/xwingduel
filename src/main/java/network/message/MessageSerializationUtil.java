package network.message;

import java.io.*;

/**
 * Created by Brian on 2/11/2016.
 */
public class MessageSerializationUtil {

    public static byte[] serialize(Message message) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream((bos))) {
            out.writeObject(message);
            return bos.toByteArray();
        }
    }

    public static Message deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        // try with resources -- auto closes these objects when finished
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
            return (Message)in.readObject();
        }
    }
}
