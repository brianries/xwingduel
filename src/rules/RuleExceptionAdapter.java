package rules;

import combat.Attacker;
import combat.Defender;
import combat.DicePool;
import state.BoardState;

/**
 * Created by Brian on 2/8/2016.
 *
 * Adapter class for RuleException as it will contain many methods for each step of every Phase.  Only a small amount
 * of methods will be implemented for each special rule
 *
 */
public class RuleExceptionAdapter implements RuleException {

    @Override
    public void modifyAttackDicePool(BoardState state, Attacker attacker, Defender defender, DicePool pool) { }
}
