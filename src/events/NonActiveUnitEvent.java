package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class NonActiveUnitEvent {
    public NonActiveUnitEvent(UnitId uid) {
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Non Active Unit Event: " + uid);
        }
    }
}
