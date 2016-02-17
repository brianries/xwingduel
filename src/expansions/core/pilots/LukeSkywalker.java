package expansions.core.pilots;

import base.Pilot;
import expansions.core.abilities.DefenseFocusToEvade;
import expansions.core.ships.TieFighter;
import expansions.core.support.Unique;
import expansions.core.upgrades.Astromech;
import expansions.core.upgrades.Modification;
import expansions.core.upgrades.Torpedoes;

/**
 * Created by dsayles on 12/19/15.
 */
public class LukeSkywalker extends Pilot implements Unique {
    Torpedoes torpedoUpgrade;
    Astromech astromechUpgrade;
    Modification modificationUpgrade;

    public LukeSkywalker() {
        pilotAbility = new DefenseFocusToEvade();
    }

    @Override
    public void initialize() {
        name = "Luke Skywalker";
        pointCost = 28;
        pilotableShips.add(new TieFighter());
        pilotableShips.forEach(
            ship -> {
                ship.setPilot(this);
                skill = 8;
            }
        );
    }
}
