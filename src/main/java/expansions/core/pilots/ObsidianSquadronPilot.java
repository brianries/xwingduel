package expansions.core.pilots;

import base.Pilot;
import expansions.core.ships.TieFighter;

/**
 * Created by dsayles on 12/19/15.
 */
public class ObsidianSquadronPilot extends Pilot {

    @Override
    public void initialize() {
        name = "Obsidian Squadron Pilot";
        pointCost = 13;
        pilotableShips.add(new TieFighter());
        pilotableShips.forEach(
            ship -> {
                ship.setPilot(this);
                skill = 3;
            }
        );
    }
}
