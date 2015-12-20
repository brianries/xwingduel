package movement;

import javafx.scene.Node;
import javafx.scene.transform.Transform;

/**
 * Created by Brian on 12/7/2015.
 */
public class MovementTemplateTalonRoll extends MovementTemplateTurnBase {

    public MovementTemplateTalonRoll(MovementTurnDirection direction, MovementDifficulty difficulty, MovementLength length) {
        super(difficulty, length, direction, MovementTurnType.TALON);
    }
}
