package sample.phases;

/**
 * Created by dsayles on 12/1/15.
 */
public enum Phase {
    PLANNING,
    //   choose maneuver
    ACTIVATION,
    //   ships activate from lowest PS to highest
    //   pre-reveal
    //   reveal dial
    //   post-reveal
    //   pre-maneuver
    //   maneuver
    //   post-maneuver
    //   pre-action
    //   action
    //   post-action
    //
    COMBAT,
    //   ships fire from highest PS to lowest
    //   choose target
    //   check firing arc
    //   check range
    //   gather attack dice
    //   add attack die for range 1 if primary weapon
    //   roll attack dice  (weapon value)
    //   modify post attack
    //   gather defense dice
    //   add defense die for range 3 if primary weapon
    //   add defense die for obstructions
    //   roll defense dice (agility)
    //   modify post defense
    //   compare dice results
    //   suffer damage to shield
    //   suffer damage to hull
    //   suffer crits to hull
    END
    // clear unused tokens as needed
    ;
}
