package expansions.core.pilots;

import base.Pilot;
import expansions.core.abilities.DefenseAttackingShipsCannotSpendFocusTokensOrRerollAttackDie;
import expansions.core.ships.TieFighter;
import expansions.core.support.Unique;

/**
 * Created by dsayles on 12/19/15.
 */
public class DarkCurse extends Pilot implements Unique {

    public DarkCurse() {
        pilotAbility = new DefenseAttackingShipsCannotSpendFocusTokensOrRerollAttackDie();
    }

    @Override
    public void initialize() {
        name = "Dark Curse";
        pointCost = 16;
        pilotableShips.add(new TieFighter());
        pilotableShips.forEach(
            ship -> {
                ship.setPilot(this);
                skill = 6;
            }
        );
    }
}
