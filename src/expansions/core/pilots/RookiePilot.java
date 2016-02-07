package expansions.core.pilots;

import base.Pilot;
import expansions.core.ships.TieFighter;

/**
 * Created by dsayles on 12/19/15.
 */
public class RookiePilot extends Pilot {

    @Override
    public void initialize() {
        name = "Academy Pilot";
        pointCost = 12;
        pilotableShips.add(new TieFighter());
        pilotableShips.forEach(
            ship -> {
                ship.setPilot(this);
                skill = 1;
            }
        );
    }
}
