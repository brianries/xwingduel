package network.message.server.command;

import network.message.server.ServerCommand;

/**
 * Created by Brian on 3/5/2016.
 */
public class BoardStateUpdateCommand extends ServerCommand {

    @Override
    public Type getMessageType() {
        return Type.BOARD_STATE_UPDATE;
    }
}
