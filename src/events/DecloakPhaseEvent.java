package events;

import base.Unit;
import base.UnitId;
import base.UnitRegistry;
import expansions.core.support.Cloakable;

/**
 * Created by dsayles on 2/24/16.
 */
public class DecloakPhaseEvent extends Event {
    public DecloakPhaseEvent(UnitId uid) {
        super(uid);
        Unit unit = UnitRegistry.getUnit(uid);
        if (unit instanceof Cloakable) {
            if (unit.isActive() && ((Cloakable)unit).isCloaked()) {
                System.out.println("Check for decloak: "+ uid);
            }
        }
    }
}
