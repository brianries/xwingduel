package base;

import base.actions.BarrelRoll;
import base.actions.Evade;
import base.actions.Focus;
import base.actions.TargetLock;

/**
 * Created by dsayles on 12/19/15.
 */
public enum Actions {
    BARREL_ROLL(new BarrelRoll()),
    EVADE(new Evade()),
    FOCUS(new Focus()),
    TARGET_LOCK(new TargetLock());

    private Action action;
    Actions(Action action) {
        this.action = action;
    }
}
