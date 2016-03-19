package network.message.server;

import network.message.Message;

/**
 * Created by Brian on 3/18/2016.
 */
public abstract class ServerResponse implements Message {
    @Override
    public CommandOrResponse getCommandOrResponse() {
        return CommandOrResponse.RESPONSE;
    }
}
