package expansions.core.upgrades.torpedoes;

import base.Actions;
import base.RequiredAction;
import base.Upgrade;
import base.UpgradeType;
import expansions.core.upgrades.Torpedoes;

/**
 * Created by dsayles on 2/7/16.
 */
public class ProtonTorpedoes implements Upgrade, Torpedoes {
    public UpgradeType getType() {return UpgradeType.TORPEDOES;}
    RequiredAction requires;

    public ProtonTorpedoes() {
        requires = new RequiredAction(Actions.TARGET_LOCK);
    }
}
