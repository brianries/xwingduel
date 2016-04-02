package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class PreSelectActionEvent extends Event {
    public PreSelectActionEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Pre Select Action Event: " + uid);
        } else {
            new NonActiveUnitEvent(uid);
        }
    }
}
