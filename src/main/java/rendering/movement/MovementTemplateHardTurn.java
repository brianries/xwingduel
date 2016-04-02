package rendering.movement;

import movement.MovementDifficulty;
import movement.MovementLength;
import movement.MovementTurnDirection;
import movement.MovementTurnType;

/**
 * Created by Brian on 12/7/2015.
 */
public class MovementTemplateHardTurn extends MovementTemplateTurnBase {

    public MovementTemplateHardTurn(MovementDifficulty difficulty, MovementLength length, MovementTurnDirection direction) {
        super(difficulty, length, direction, MovementTurnType.HARD);
    }
}
