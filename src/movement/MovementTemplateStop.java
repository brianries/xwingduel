package movement;

import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

/**
 * Created by Brian on 12/19/2015.
 */
public class MovementTemplateStop extends MovementTemplateForwardBase {

    public MovementTemplateStop(MovementDifficulty difficulty) {
        super(difficulty, MovementLength.ZERO, MovementForwardType.STOP);
    }
}
