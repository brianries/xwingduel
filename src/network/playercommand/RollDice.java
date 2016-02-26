package network.playercommand;

import java.io.Serializable;

/**
 * Created by Brian on 2/11/2016.
 */
public class RollDice implements Serializable {

    public int numDice;

    public RollDice(int numDice) {
        this.numDice = numDice;
    }
}
