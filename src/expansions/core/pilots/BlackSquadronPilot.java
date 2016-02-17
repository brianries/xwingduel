package expansions.core.pilots;

import base.Pilot;
import expansions.core.ships.TieFighter;
import expansions.core.upgrades.Modification;

/**
 * Created by dsayles on 12/19/15.
 */
public class BlackSquadronPilot extends Pilot {
    Modification modificationUpgrade;

    @Override
    public void initialize() {
        name = "Black Squadron Pilot";
        pointCost = 14;
        pilotableShips.add(new TieFighter());
        pilotableShips.forEach(
            ship -> {
                ship.setPilot(this);
                skill = 4;
            }
        );
    }
}
