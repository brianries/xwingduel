package network.message;

import java.io.Serializable;

/**
 * Created by Brian on 3/18/2016.
 */
public interface Message extends Serializable {

    CommandOrResponse getCommandOrResponse();

    Type getMessageType();

    enum CommandOrResponse {
        COMMAND,
        RESPONSE,
    }

    enum Type {
        ACTION,
        ADD_SQUADRON,
        BOARD_STATE_UPDATE,
        DECLARE_TARGET,
        DECLOAK,
        MANEUVER,
        MODIFY_DICE,
        PLACE_OBSTACLE,
        PLACE_SHIP,
        REROLL_DICE,
        ROLL_DICE,
        UPDATE_MESSAGE,
    }
}
