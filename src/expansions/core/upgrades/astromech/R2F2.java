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
    public UpgradeType getType() {return UpgradeType.ASTROMECH;}

    @Override
    public void registerPhase(UnitId unitId) {
        Unit unit = UnitRegistry.getUnit(unitId);
        unit.addAction(new IncreaseAgility());
    }
}
