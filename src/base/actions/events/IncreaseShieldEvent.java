package base.actions.events;

import base.Unit;
import base.UnitId;
import base.UnitRegistry;
import events.Event;

/**
 * Created by dsayles on 2/25/16.
 */
public class IncreaseShieldEvent extends Event {
    public IncreaseShieldEvent(UnitId uid) {
        super(uid);
        Unit unit = UnitRegistry.getUnit(uid);

        if (unit.getShip().getShields() > unit.getShields()) {
            unit.incrementShields();
        }
    }
}
