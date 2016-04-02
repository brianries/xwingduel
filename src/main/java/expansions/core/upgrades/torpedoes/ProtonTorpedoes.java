package expansions.core.upgrades.torpedoes;

import base.*;
import expansions.core.upgrades.Torpedoes;
import phases.Phase;
import phases.PhaseStateManager;

/**
 * Created by dsayles on 2/7/16.
 */
public class ProtonTorpedoes implements Upgrade, Torpedoes {

    private RequiredAction requires;

    public ProtonTorpedoes() {
        requires = new RequiredAction(Actions.TARGET_LOCK);
    }

    @Override
    public UpgradeType getType() {
        return UpgradeType.TORPEDOES;
    }

    @Override
    public int getPointCost() {
        return 4;
    }

    @Override
    public void registerPhase(UnitId unitId) {
        PhaseStateManager.registerUnitIdForPhase(Phase.MODIFY_POST_ATTACK_ATTACKER, unitId);
    }
}
