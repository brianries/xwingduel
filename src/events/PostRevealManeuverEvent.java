package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class PostRevealManeuverEvent extends Event {
    public PostRevealManeuverEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Post Reveal Maneuver Event: " + uid);
        } else {
            new NonActiveUnitEvent(uid);
        }
    }
}
