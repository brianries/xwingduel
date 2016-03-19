package network.message.server.response;

import network.message.server.ServerResponse;

/**
 * Created by Brian on 3/18/2016.
 */
public class RollDiceResponse extends ServerResponse {

    @Override
    public Type getMessageType() {
        return Type.ROLL_DICE;
    }
}
