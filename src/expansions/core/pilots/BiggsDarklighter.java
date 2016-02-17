package expansions.core.pilots;

import base.Pilot;
import expansions.core.abilities.DefenderRange1RetargetSelf;
import expansions.core.ships.TieFighter;
import expansions.core.support.Unique;
import expansions.core.upgrades.Astromech;
import expansions.core.upgrades.Torpedoes;

/**
 * Created by dsayles on 12/19/15.
 */
public class BiggsDarklighter extends Pilot implements Unique {
    Torpedoes torpedoUpgrade;
    Astromech astromechUpgrade;

    public BiggsDarklighter() {
        pilotAbility = new DefenderRange1RetargetSelf();
    }

    @Override
    public void initialize() {
        name = "Biggs Darklighter";
        pointCost = 25;
        pilotableShips.add(new TieFighter());
        pilotableShips.forEach(
            ship -> {
                ship.setPilot(this);
                skill = 5;
            }
        );
    }
}
