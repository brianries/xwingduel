package expansions.core.ships;

import base.Actions;
import base.Ship;

import java.util.EnumSet;

/**
 * Created by dsayles on 12/19/15.
 */
public class TieFighter extends Ship {
    public TieFighter() {
        setAttack(2);
        setAgility(3);
        setHull(3);
        setShields(0);
        setActions(EnumSet.of(Actions.BARREL_ROLL, Actions.EVADE, Actions.FOCUS));
//        setUpgrades(null);
        name = "Tie/ln";
    }
}
