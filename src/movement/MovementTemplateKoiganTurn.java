package movement;

import javafx.scene.transform.Transform;

/**
 * Created by Brian on 12/19/2015.
 */
public class MovementTemplateKoiganTurn extends MovementTemplateForwardBase {

    public MovementTemplateKoiganTurn(MovementDifficulty difficulty, MovementLength length) {
        super(difficulty, length, MovementForwardType.KTURN);
    }
}
