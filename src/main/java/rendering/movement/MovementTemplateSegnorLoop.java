package rendering.movement;

import movement.MovementDifficulty;
import movement.MovementLength;
import movement.MovementTurnDirection;
import movement.MovementTurnType;

/**
 * Created by Brian on 12/19/2015.
 */
public class MovementTemplateSegnorLoop extends MovementTemplateTurnBase {

    public MovementTemplateSegnorLoop(MovementDifficulty difficulty, MovementLength length, MovementTurnDirection direction) {
        super(difficulty, length, direction, MovementTurnType.SLOOP);
    }
}