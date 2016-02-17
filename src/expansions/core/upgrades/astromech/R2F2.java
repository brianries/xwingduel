package expansions.core.upgrades.astromech;

import base.Upgrade;
import base.UpgradeType;
import expansions.core.support.Unique;
import expansions.core.upgrades.Astromech;

/**
 * Created by dsayles on 2/7/16.
 */
public class R2F2 implements Unique, Upgrade, Astromech {
    public UpgradeType getType() {return UpgradeType.ASTROMECH;}
}
