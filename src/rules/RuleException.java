package rules;

import combat.Attacker;
import combat.Defender;
import combat.DicePool;
import state.BoardState;

/**
 * Created by Brian on 2/1/2016.
 */
public interface RuleException {

    void modifyAttackDicePool(BoardState state, Attacker attacker, Defender defender, DicePool pool);
}
