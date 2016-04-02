package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class EndPhaseEvent extends Event {
    public EndPhaseEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("End Phase Event: " + uid);
        } else {
            new NonActiveUnitEvent(uid);
        }
    }
}
