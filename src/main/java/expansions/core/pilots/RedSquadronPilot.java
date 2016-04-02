package expansions.core.pilots;

import base.Pilot;
import expansions.core.ships.TieFighter;
import expansions.core.upgrades.Astromech;
import expansions.core.upgrades.Torpedoes;

/**
 * Created by dsayles on 12/19/15.
 */
public class RedSquadronPilot extends Pilot {
    Torpedoes torpedoUpgrade;
    Astromech astromechUpgrade;

    @Override
    public void initialize() {
        name = "Red Squadron Pilot";
        pointCost = 23;
        pilotableShips.add(new TieFighter());
        pilotableShips.forEach(
            ship -> {
                ship.setPilot(this);
                skill = 4;
            }
        );
    }
}
