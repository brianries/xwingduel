package phases;

/**
 * Created by dsayles on 12/1/15.
 */
public enum Phase {
    PLANNING(),
    //   choose maneuver
    DECLOAK(),
    //   ships activate from lowest PS to highest
    //   decloak
    ACTIVATION(),
    //   ships activate from lowest PS to highest
    //   pre-reveal
    PRE_REVEAL_MANEUVER(),
    //   reveal dial
    REVEAL_MANEUVER(),
    //   post-reveal
    POST_REVEAL_MANEUVER(),
    //   pre-maneuver
    PRE_MANEUVER(),
    //   maneuver
    MANEUVER(),
    //   post-maneuver
    POST_MANEUVER(),
    //   pre-action
    PRE_SELECT_ACTION(),
    //   action
    SELECT_ACTION(),
    //   post-action
    POST_SELECT_ACTION(),
    //
    COMBAT(),
    //   ships fire from highest PS to lowest
//    Declare Target
    DECLARE_TARGET(),
    //   choose target
    CHOOSE_TARGET(),
    //   check firing arc
    CHECK_FIRING_ARC(),
    //   check range
    CHECK_RANGE(),

//    Roll Attack Dice
    ATTACK(),
    //   gather attack dice
    GATHER_ATTACK_DICE(),
    //   add attack die for range 1 if primary weapon
    INCREASE_FOR_RANGE_1(),
    //   roll attack dice  (weapon value)
    ROLL_ATTACK_DICE(),
    //   modify post attack (Defender)
    MODIFY_POST_ATTACK_DEFENDER(),
    //   modify post attack (Attacker)
    MODIFY_POST_ATTACK_ATTACKER(),

//    Roll Defense Dice
    DEFEND(),
    //   gather defense dice
    GATHER_DEFENSE_DICE(),
    //   add defense die for range 3 if primary weapon
    INCREASE_FOR_RANGE_3(),
    //   add defense die for obstructions
    INCREASE_FOR_OBSTRUCTED(),
    //   roll defense dice (agility)
    ROLL_DEFENSE_DICE(),
    //   modify post defense (Attacker)
    MODIFY_POST_DEFENSE_ATTACKER(),
    //   modify post defense (Defender)
    MODIFY_POST_DEFENSE_DEFENDER(),

    COMPARE_RESULTS(),
    DEAL_DAMAGE(),
    SUFFER_DAMAGE(),
    SUFFER_DAMAGE_TO_SHIELDS(),
    SUFFER_DAMAGE_TO_HULL(),
    SUFFER_CRIT_TO_HULL(),
    END()
    // clear unused tokens as needed
    ;

    private Phase(){}
}
