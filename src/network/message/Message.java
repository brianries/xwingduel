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
        ADD_SHIP,
        ADD_OBSTACLE,
        BOARD_STATE_UPDATE,
        DECLARE_TARGET,
        DECLOAK,
        MANEUVER,
        MODIFY_DICE,
        REROLL_DICE,
        ROLL_DICE,
        SHIP_PLACEMENT,
        UPDATE_MESSAGE,
    }
}
