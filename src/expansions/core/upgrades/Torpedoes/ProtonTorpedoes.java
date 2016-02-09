package expansions.core.upgrades.torpedoes;

import base.Actions;
import base.RequiredAction;
import expansions.core.upgrades.Torpedoes;

/**
 * Created by dsayles on 2/7/16.
 */
public class ProtonTorpedoes implements Torpedoes {
    RequiredAction requires;

    public ProtonTorpedoes() {
        requires = new RequiredAction(Actions.TARGET_LOCK);
    }
}
