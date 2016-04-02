package rendering.movement;

import movement.MovementDifficulty;
import movement.MovementForwardType;
import movement.MovementLength;

/**
 * Created by Brian on 12/19/2015.
 */
public class MovementTemplateForward extends MovementTemplateForwardBase {

    public MovementTemplateForward(MovementDifficulty difficulty, MovementLength length) {
        super(difficulty, length, MovementForwardType.FORWARD);
    }
}
