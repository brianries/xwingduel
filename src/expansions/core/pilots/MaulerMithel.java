package expansions.core.pilots;

import base.Pilot;
import expansions.core.abilities.AttackRange1AddAttackDie;
import expansions.core.ships.TieFighter;
import expansions.core.support.Unique;

/**
 * Created by dsayles on 12/19/15.
 */
public class MaulerMithel extends Pilot implements Unique {
    public MaulerMithel() {
        pilotAbility = new AttackRange1AddAttackDie();
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
