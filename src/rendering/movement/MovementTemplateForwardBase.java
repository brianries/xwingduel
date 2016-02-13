package rendering.movement;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Translate;
import movement.MovementDifficulty;
import movement.MovementForwardType;
import movement.MovementLength;
import movement.MovementType;

/**
 * Created by Brian on 12/19/2015.
 */
public abstract class MovementTemplateForwardBase extends MovementTemplate {

    public MovementTemplateForwardBase(MovementDifficulty difficulty, MovementLength length, MovementForwardType forwardType) {
        super(difficulty, getMovementType(length, forwardType));
        init(difficulty, length);
    }

    private static MovementType getMovementType(MovementLength length, MovementForwardType forwardType) {
        switch (length) {
            case ONE:
                return (forwardType == MovementForwardType.FORWARD) ? MovementType.FORWARD_1 : MovementType.K_TURN_1;
            case TWO:
                return (forwardType == MovementForwardType.FORWARD) ? MovementType.FORWARD_2 : MovementType.K_TURN_2;
            case THREE:
                return (forwardType == MovementForwardType.FORWARD) ? MovementType.FORWARD_3 : MovementType.K_TURN_3;
            case FOUR:
                return (forwardType == MovementForwardType.FORWARD) ? MovementType.FORWARD_4 : MovementType.K_TURN_4;
            case FIVE:
                return (forwardType == MovementForwardType.FORWARD) ? MovementType.FORWARD_5 : MovementType.K_TURN_5;
            default:
                throw new RuntimeException("Invalid movement length " + length.name());
        }
    }

    private static double getLengthMM(MovementLength length) {
        switch (length) {
            case ONE:
                return 40.0;
            case TWO:
                return 80.0;
            case THREE:
                return 120.0;
            case FOUR:
                return 160.0;
            case FIVE:
                return 200.0;
            default:
                throw new RuntimeException("Invalid movement length " + length.name());
        }
    }

    private void init(MovementDifficulty difficulty, MovementLength movementLength) {
        double length = getLengthMM(movementLength);
        this.getElements().add(new MoveTo(0, -MOVEMENT_TEMPLATE_WIDTH /2.0));
        this.getElements().add(new LineTo(length, -MOVEMENT_TEMPLATE_WIDTH /2.0));
        this.getElements().add(new LineTo(length, MOVEMENT_TEMPLATE_WIDTH /2.0));
        this.getElements().add(new LineTo(0, MOVEMENT_TEMPLATE_WIDTH /2.0));
        this.setFill(getColorForDifficulty(difficulty));
        this.setStroke(Color.WHITE);
        this.setFillRule(FillRule.NON_ZERO);

        Translate translate = new Translate(length, 0);
        endTransform.append(translate);
    }
}
