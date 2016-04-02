package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class ManeuverEvent extends Event {
    public ManeuverEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Manuever Event: " + uid);
        } else {
            new NonActiveUnitEvent(uid);
        }
    }
}
