package network.message.player.command;

import network.message.Message;
import network.message.player.PlayerCommand;
import rendering.obstacles.Obstacle;

/**
 * Created by Brian on 3/6/2016.
 */
public class PlaceObstacleCommand extends PlayerCommand {

    private Obstacle obstacle;

    public PlaceObstacleCommand(Obstacle obstacle) {

    }

    @Override
    public Message.Type getMessageType() {
        return Message.Type.PLACE_OBSTACLE;
    }
}