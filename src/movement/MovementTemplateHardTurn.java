package movement;

import javafx.scene.transform.Transform;

/**
 * Created by Brian on 12/7/2015.
 */
public class MovementTemplateHardTurn extends MovementTemplateTurnBase {

    public MovementTemplateHardTurn(MovementDifficulty difficulty, MovementLength length, MovementTurnDirection direction) {
        super(difficulty, length, direction, MovementTurnType.HARD);
    }

    @Override
    public Transform getEndTransform() {
        return null;
    }
}
