package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class CombatPhaseEvent extends Event {
    public CombatPhaseEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Combat Phase Event: " + uid);
        } else {
            new NonActiveUnitEvent(uid);
        }
    }
}
