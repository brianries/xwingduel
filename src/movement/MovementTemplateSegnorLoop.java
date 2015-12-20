package movement;

import javafx.scene.transform.Transform;

/**
 * Created by Brian on 12/19/2015.
 */
public class MovementTemplateSegnorLoop extends MovementTemplateTurnBase {

    public MovementTemplateSegnorLoop(MovementDifficulty difficulty, MovementLength length, MovementTurnDirection direction) {
        super(difficulty, length, direction, MovementTurnType.SLOOP);
    }
}