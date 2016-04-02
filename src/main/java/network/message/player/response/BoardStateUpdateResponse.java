package network.message.player.response;

import network.message.player.PlayerResponse;

/**
 * Created by Brian on 3/18/2016.
 */
public class BoardStateUpdateResponse extends PlayerResponse {

    @Override
    public Type getMessageType() {
        return Type.BOARD_STATE_UPDATE;
    }
}
