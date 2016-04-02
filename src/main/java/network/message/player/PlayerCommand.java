package network.message.player;

import network.message.Message;

/**
 * Created by Brian on 2/11/2016.
 */
public abstract class PlayerCommand implements Message {
    @Override
    public CommandOrResponse getCommandOrResponse() {
        return CommandOrResponse.COMMAND;
    }
}
