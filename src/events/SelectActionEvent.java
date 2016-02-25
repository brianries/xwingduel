package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class SelectActionEvent extends Event {
    public SelectActionEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Select Action Event: " + uid);
        } else {
            new NonActiveUnitEvent(uid);
        }
    }
}
