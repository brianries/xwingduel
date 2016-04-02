package expansions.core.upgrades.astromech;

import base.*;
import base.actions.IncreaseAgility;
import expansions.core.support.Unique;
import expansions.core.upgrades.Astromech;
import phases.Phase;
import phases.PhaseStateManager;

/**
 * Created by dsayles on 2/7/16.
 */
public class R2F2 implements Unique, Upgrade, Astromech {

    @Override
    public UpgradeType getType() {
        return UpgradeType.ASTROMECH;
    }

    @Override
    public int getPointCost() {
        return 3;
    }

    @Override
    public void registerPhase(UnitId unitId) {
        Unit unit = UnitRegistry.getUnit(unitId);
        unit.addAction(new IncreaseAgility());
    }
}
