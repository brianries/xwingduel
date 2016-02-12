package network;

import java.io.Serializable;

/**
 * Created by Brian on 2/11/2016.
 */
public class RollDiceCommand implements Serializable {

    public int numDice;

    public RollDiceCommand(int numDice) {
        this.numDice = numDice;
    }
}
