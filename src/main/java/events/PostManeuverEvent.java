package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class PostManeuverEvent extends Event {
    public PostManeuverEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Post Maneuver Event: " + uid);
        } else {
            new NonActiveUnitEvent(uid);
        }
    }
}
