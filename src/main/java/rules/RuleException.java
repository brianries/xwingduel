package rules;

import combat.Attacker;
import combat.Defender;
import combat.DicePool;
import state.BoardState;

/**
 * Created by Brian on 2/1/2016.
 *
 * This will be the giant interface class that every upgrade card / pilot skill will have to implement.
 */
public interface RuleException {

    void modifyAttackDicePool(BoardState state, Attacker attacker, Defender defender, DicePool pool);
}
