package base.actions.events;

import base.Actions;
import base.Unit;
import base.UnitId;
import base.UnitRegistry;
import base.actions.TargetLock;
import dws.UIFaker;
import events.Event;
import phases.PhaseStateManager;
import phases.TargetLockManager;

import java.util.Set;

/**
 * Created by dsayles on 2/24/16.
 */
public class AttemptTargetLockEvent extends Event {
    public AttemptTargetLockEvent(UnitId attacker) {
        super(attacker);

        Unit attackUnit = UnitRegistry.getUnit(attacker);
        if (attackUnit.isActive() && attackUnit.getShip().hasAction(Actions.TARGET_LOCK)) {

            Set<UnitId> uids = PhaseStateManager.getEnemySquadSet(attacker);
            UnitId[] uida = uids.toArray(new UnitId[uids.size()]);
            String[] choices = new String[uids.size()];
            int i = 0;
            for (UnitId uid : uids) {
                Unit unit = UnitRegistry.getUnit(uid);
                choices[i++] = unit.getShip().name +" "+ unit.getPilot() +" "+ unit.getPilotSkill();
            }

            int result = UIFaker.presentChoices(choices);

            TargetLock tl = new TargetLock(attacker, uida[result]);
            attackUnit.addAction(tl);
            TargetLockManager.registerTargetLock(tl);
        }
    }
}
