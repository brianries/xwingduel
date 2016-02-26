package expansions.core.upgrades.elitepilottalent;

import base.Damage;
import base.UnitId;
import base.Upgrade;
import base.UpgradeType;
import expansions.core.upgrades.ElitePilotTalent;
import phases.Phase;
import phases.PhaseStateManager;

/**
 * Created by dsayles on 2/7/16.
 */
public class Determination implements Upgrade, ElitePilotTalent {
    public UpgradeType getType() {return UpgradeType.ELITE_PILOT_TALENT;}

    @Override
    public void registerPhase(UnitId unitId) {
        PhaseStateManager.registerUnitIdForPhase(Phase.SUFFER_CRIT_TO_HULL, unitId);
    }

    public Damage interceptPilotCritical(Damage damage) {
        if (damage.affectsPilot()) {
            return null;
        }
        return damage;
    }
}
