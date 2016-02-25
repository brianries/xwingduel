package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class PreRevealManeuverEvent extends Event {
    public PreRevealManeuverEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Pre Reveal Maneuver Event: " + uid);
        } else {
            new NonActiveUnitEvent(uid);
        }
    }
}
