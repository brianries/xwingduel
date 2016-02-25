package events;

import base.UnitId;
import base.UnitRegistry;

/**
 * Created by dsayles on 2/24/16.
 */
public class ActivationPhaseEvent extends Event {
    public ActivationPhaseEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Activation Phase Event: " + uid);
            new PreRevealManeuverEvent(uid);
            new RevealManeuverEvent(uid);
            new PostRevealManeuverEvent(uid);
            new PreManeuverEvent(uid);
            new ManeuverEvent(uid);
            new PostManeuverEvent(uid);
            new PreSelectActionEvent(uid);
            new SelectActionEvent(uid);
            new PostSelectActionEvent(uid);
        }
    }
}
