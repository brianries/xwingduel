package events;

import base.Actions;
import base.Unit;
import base.UnitId;
import base.UnitRegistry;
import base.actions.*;
import base.actions.events.AttemptTargetLockEvent;
import dws.UIFaker;

import java.util.EnumSet;

/**
 * Created by dsayles on 2/24/16.
 */
public class SelectActionEvent extends Event {
    public SelectActionEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Select Action Event: " + uid);
            Unit unit = UnitRegistry.getUnit(uid);
            EnumSet<Actions> availableActions = unit.getShip().getActions();

            String [] actionSelections;
            if (!availableActions.isEmpty()) {
                actionSelections = new String[availableActions.size()];
                int i = 0;

                for (Actions act: availableActions) {
                    actionSelections[i++] = act.name();
                }

                int result = UIFaker.presentChoices(actionSelections);

                switch(Actions.valueOf(actionSelections[result])) {
                    case NO_ACTION:
                        break;
                    case BARREL_ROLL:
                        break;
                    case EVADE:
                        break;
                    case FOCUS:
                        break;
                    case TARGET_LOCK:
                        new AttemptTargetLockEvent(uid);
                        break;
                    case BOOST:
                        break;
                    case CLOAK:
                        break;
                    case SLAM:
                        break;
                }
            }

        } else {
            new NonActiveUnitEvent(uid);
        }
    }
}
