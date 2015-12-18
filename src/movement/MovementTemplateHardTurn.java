package movement;

import javafx.scene.Node;
import javafx.scene.transform.Transform;

/**
 * Created by Brian on 12/7/2015.
 */
public class MovementTemplateHardTurn extends MovementTemplateHardTurnBase {

    public MovementTemplateHardTurn(MovementTurnDirection direction, MovementDifficulty difficulty, MovementLength length) {
        super(direction, difficulty, length);
    }

    @Override
    public Transform getEndTransform() {
        return null;
    }

    @Override
    public Node getDisplayToken() {
        return null;
    }

}
