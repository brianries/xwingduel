package network.message.player.command;

import network.message.player.PlayerCommand;

/**
 * Created by Brian on 2/11/2016.
 */
public class RollDiceCommand extends PlayerCommand {

    public int numDice;

    public RollDiceCommand(int numDice) {
        this.numDice = numDice;
    }

    @Override
    public Type getMessageType() {
        return Type.ROLL_DICE;
    }
}
