package network.message.server.command;

import network.message.server.ServerCommand;

/**
 * Created by Brian on 2/29/2016.
 */
public class UpdateMessage extends ServerCommand {

    private String message;

    public UpdateMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public Type getMessageType() {
        return Type.UPDATE_MESSAGE;
    }
}
