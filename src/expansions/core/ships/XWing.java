package expansions.core.ships;

import base.Actions;
import base.Ship;

import java.util.EnumSet;

/**
 * Created by dsayles on 12/19/15.
 */
public class XWing extends Ship {
    public XWing() {
        setAttack(3);
        setAgility(2);
        setHull(3);
        setShields(2);
        setActions(EnumSet.of(Actions.FOCUS, Actions.TARGET_LOCK));
    }


}
