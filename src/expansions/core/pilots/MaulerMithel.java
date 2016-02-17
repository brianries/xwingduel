package expansions.core.pilots;

import base.Pilot;
import expansions.core.abilities.AttackRange1AddAttackDie;
import expansions.core.ships.TieFighter;
import expansions.core.support.Unique;
import expansions.core.upgrades.Modification;

/**
 * Created by dsayles on 12/19/15.
 */
public class MaulerMithel extends Pilot implements Unique {
    Modification modificationUpgrade;

    public MaulerMithel() {
        pilotAbility = new AttackRange1AddAttackDie();
    }
    @Override
    public void initialize() {
        name = "Mauler Mithel";
        pointCost = 17;
        pilotableShips.add(new TieFighter());
        pilotableShips.forEach(
            ship -> {
                ship.setPilot(this);
                skill = 7;
            }
        );
    }
}
