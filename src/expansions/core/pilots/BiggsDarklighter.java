package expansions.core.pilots;

import base.Pilot;
import expansions.core.abilities.DefenderRange1RetargetSelf;
import expansions.core.ships.TieFighter;
import expansions.core.support.Unique;

/**
 * Created by dsayles on 12/19/15.
 */
public class BiggsDarklighter extends Pilot implements Unique {

    public BiggsDarklighter() {
        pilotAbility = new DefenderRange1RetargetSelf();
    }

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
