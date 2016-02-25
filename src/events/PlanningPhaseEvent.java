package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class PlanningPhaseEvent extends Event {
    public PlanningPhaseEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Planning Phase Event: " + uid);
        } else {
            new NonActiveUnitEvent(uid);
        }
    }
}
