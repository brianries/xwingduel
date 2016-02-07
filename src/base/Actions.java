package base;

import base.actions.*;


/**
 * Created by dsayles on 12/19/15.
 */
public enum Actions implements Action {
    BARREL_ROLL(new BarrelRoll()),
    EVADE(new Evade()),
    FOCUS(new Focus()),
    TARGET_LOCK(new TargetLock()),
    BOOST(new Boost()),
    CLOAK(new Cloak()),
    SLAM(new Slam());

    private Action action;
    Actions(Action action) {
        this.action = action;
    }
}
