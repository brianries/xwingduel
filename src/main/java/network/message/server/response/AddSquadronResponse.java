package network.message.server.response;

import network.message.server.ServerResponse;

/**
 * Created by Brian on 3/19/2016.
 */
public class AddSquadronResponse extends ServerResponse {

    @Override
    public Type getMessageType() {
        return Type.ADD_SQUADRON;
    }
}
