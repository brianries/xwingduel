package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class RevealManeuverEvent extends Event {
    public RevealManeuverEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Reveal Maneuver Event: " + uid);
        } else {
            new NonActiveUnitEvent(uid);
        }
    }
}
