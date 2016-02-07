package expansions.core.pilots;

import base.Pilot;
import expansions.core.abilities.DefenseFocusToEvade;
import expansions.core.ships.TieFighter;
import expansions.core.support.Unique;

/**
 * Created by dsayles on 12/19/15.
 */
public class LukeSkywalker extends Pilot implements Unique {
    public LukeSkywalker() {
        pilotAbility = new DefenseFocusToEvade();
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
