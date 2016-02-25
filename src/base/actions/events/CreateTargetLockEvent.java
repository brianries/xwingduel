package base.actions.events;

import base.Actions;
import base.Unit;
import base.UnitId;
import base.UnitRegistry;
import base.actions.TargetLock;
import events.Event;
import phases.TargetLockManager;

/**
 * Created by dsayles on 2/24/16.
 */
public class CreateTargetLockEvent extends Event {
    public CreateTargetLockEvent(UnitId attacker, UnitId defender) {
        super(attacker);
        Unit attackUnit = UnitRegistry.getUnit(attacker);
        if (attackUnit.isActive() && attackUnit.getShip().hasAction(Actions.TARGET_LOCK)) {
            TargetLock tl = new TargetLock(attacker, defender);
            attackUnit.addAction(tl);
            TargetLockManager.registerTargetLock(tl);
        }
    }
}
