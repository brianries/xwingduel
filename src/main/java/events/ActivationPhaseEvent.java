package events;

import base.UnitId;
import base.UnitRegistry;
import phases.Phase;
import phases.PhaseStateManager;

/**
 * Created by dsayles on 2/24/16.
 */
public class ActivationPhaseEvent extends Event {
    public ActivationPhaseEvent(UnitId uid) {
        super(uid);
        if (UnitRegistry.getUnit(uid).isActive()) {
            System.out.println("Activation Phase Event: " + uid);
            if (PhaseStateManager.phaseContainsUnitId(Phase.PRE_REVEAL_MANEUVER, uid)) {
                new PreRevealManeuverEvent(uid);
            }

            if (PhaseStateManager.phaseContainsUnitId(Phase.REVEAL_MANEUVER, uid)) {
                new RevealManeuverEvent(uid);
            }

            if (PhaseStateManager.phaseContainsUnitId(Phase.POST_REVEAL_MANEUVER, uid)) {
                new PostRevealManeuverEvent(uid);
            }

            if (PhaseStateManager.phaseContainsUnitId(Phase.PRE_MANEUVER, uid)) {
                new PreManeuverEvent(uid);
            }

            if (PhaseStateManager.phaseContainsUnitId(Phase.MANEUVER, uid)) {
                new ManeuverEvent(uid);
            }

            if (PhaseStateManager.phaseContainsUnitId(Phase.POST_MANEUVER, uid)) {
                new PostManeuverEvent(uid);
            }

            if (PhaseStateManager.phaseContainsUnitId(Phase.PRE_SELECT_ACTION, uid)) {
                new PreSelectActionEvent(uid);
            }

            if (PhaseStateManager.phaseContainsUnitId(Phase.SELECT_ACTION, uid)) {
                new SelectActionEvent(uid);
            }

            if (PhaseStateManager.phaseContainsUnitId(Phase.POST_SELECT_ACTION, uid)) {
                new PostSelectActionEvent(uid);
            }
        }
    }
}
