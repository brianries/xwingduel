package rendering.movement;

import movement.MovementDifficulty;
import movement.MovementForwardType;
import movement.MovementLength;

/**
 * Created by Brian on 12/19/2015.
 */
public class MovementTemplateStop extends MovementTemplateForwardBase {

    public MovementTemplateStop(MovementDifficulty difficulty) {
        super(difficulty, MovementLength.ZERO, MovementForwardType.STOP);
    }
}
