package network.message.server;

import network.message.Message;

/**
 * Created by Brian on 2/29/2016.
 */
public abstract class ServerCommand implements Message {
    @Override
    public CommandOrResponse getCommandOrResponse() {
        return CommandOrResponse.COMMAND;
    }
}
