package network.message.server.response;

import network.message.Message;
import network.message.server.ServerResponse;

/**
 * Created by Brian on 3/18/2016.
 */
public class AddShipResponse extends ServerResponse {

    @Override
    public Message.Type getMessageType() {
        return Type.ADD_SHIP;
    }
}
