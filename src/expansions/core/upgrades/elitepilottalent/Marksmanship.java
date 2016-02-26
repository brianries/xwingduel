package expansions.core.upgrades.elitepilottalent;

import base.UnitId;
import base.Upgrade;
import base.UpgradeType;
import expansions.core.upgrades.ElitePilotTalent;
import phases.Phase;
import phases.PhaseStateManager;

/**
 * Created by dsayles on 2/7/16.
 */
public class Marksmanship implements Upgrade, ElitePilotTalent {
    public UpgradeType getType() {return UpgradeType.ELITE_PILOT_TALENT;}
    @Override
    public void registerPhase(UnitId unitId) {
        PhaseStateManager.registerUnitIdForPhase(Phase.MODIFY_POST_ATTACK_ATTACKER, unitId);
    }
}
