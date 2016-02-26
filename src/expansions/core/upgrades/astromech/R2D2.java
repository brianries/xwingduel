package expansions.core.upgrades.astromech;

import base.*;
import base.actions.events.IncreaseShieldEvent;
import expansions.core.support.Unique;
import expansions.core.upgrades.Astromech;
import phases.Phase;
import phases.PhaseStateManager;

/**
 * Created by dsayles on 2/7/16.
 */
public class R2D2 implements Unique, Upgrade, Astromech {
    public UpgradeType getType() {return UpgradeType.ASTROMECH;}

    @Override
    public void registerPhase(UnitId unitId) {
        PhaseStateManager.registerUnitIdForPhase(Phase.POST_MANEUVER, unitId);
    }

    public void checkManeuverType(Maneuver maneuver, UnitId unitId) {
        if (maneuver.type == ManeuverType.GREEN) {
            new IncreaseShieldEvent(unitId);
        }
    }
}
