package network.servercommand;

import java.io.Serializable;

/**
 * Created by Brian on 2/29/2016.
 */
public class UpdateMessage implements Serializable {

    private String message;

    public UpdateMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
