package base.actions;

import base.Action;
import base.BaseUpgrade;
import base.UnitId;

/**
 * Created by dsayles on 1/11/16.
 */
public class TargetLock implements Action {
    UnitId attacker;
    UnitId defender;

    public TargetLock(){}
    public TargetLock(UnitId attacker, UnitId defender) {
        this.attacker = attacker;
        this.defender = defender;

    }

    public int compareTo(TargetLock tl) {
        if (this.attacker.compareTo(tl.attacker) == 0 && this.defender.compareTo(tl.defender)==0) {
            return 0;
        }
        int att = this.attacker.compareTo(tl.attacker);
        if (att != 0) { return att; }
        return this.defender.compareTo(tl.defender);
    }
}
