package expansions.core.pilots;

import base.Pilot;
import base.UpgradeType;
import expansions.core.ships.TieFighter;
import expansions.core.upgrades.Astromech;
import expansions.core.upgrades.Torpedoes;

/**
 * Created by dsayles on 12/19/15.
 */
public class RookiePilot extends Pilot {
    Torpedoes torpedoUpgrade;
    Astromech astromechUpgrade;

    @Override
    public void initialize() {
        name = "Rookie Pilot";
        pointCost = 21;
        pilotableShips.add(new TieFighter());
        pilotableShips.forEach(
            ship -> {
                ship.setPilot(this);
                skill = 2;
            }
        );
    }
}
