package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class PreManeuverEvent extends Event {
    public PreManeuverEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Pre Maneuver Event: " + uid);
        } else {
            new NonActiveUnitEvent(uid);
        }
    }
}
