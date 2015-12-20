package movement;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Created by Brian on 12/5/2015.
 */
public class MovementTemplateFactory {


    public MovementTemplateFactory() { }


    public MovementTemplate constructMovementTemplate(MovementDifficulty difficulty, MovementType type) {
        switch (type) {
            case STOP:
                 return new MovementTemplateStop(difficulty);

            case LEFT_HARD_1:
                return new MovementTemplateHardTurn(difficulty, MovementLength.ONE, MovementTurnDirection.LEFT);
            case LEFT_BANK_1:
                return new MovementTemplateBankTurn(difficulty, MovementLength.ONE, MovementTurnDirection.LEFT);
            case FORWARD_1:
                return new MovementTemplateForward(difficulty, MovementLength.ONE);
            case RIGHT_BANK_1:
                return new MovementTemplateBankTurn(difficulty, MovementLength.ONE, MovementTurnDirection.RIGHT);
            case RIGHT_HARD_1:
                return new MovementTemplateHardTurn(difficulty, MovementLength.ONE, MovementTurnDirection.RIGHT);
            case K_TURN_1:
                return new MovementTemplateKoiganTurn(difficulty, MovementLength.ONE);
            case LEFT_SLOOP_1:
                return new MovementTemplateSegnorLoop(difficulty, MovementLength.ONE, MovementTurnDirection.LEFT);
            case RIGHT_SLOOP_1:
                return new MovementTemplateSegnorLoop(difficulty, MovementLength.ONE, MovementTurnDirection.RIGHT);
            case LEFT_TALON_1:
                return new MovementTemplateTalonRoll(difficulty, MovementLength.ONE, MovementTurnDirection.LEFT);
            case RIGHT_TALON_1:
                return new MovementTemplateTalonRoll(difficulty, MovementLength.ONE, MovementTurnDirection.RIGHT);

            case LEFT_HARD_2:
                return new MovementTemplateHardTurn(difficulty, MovementLength.TWO, MovementTurnDirection.LEFT);
            case LEFT_BANK_2:
                return new MovementTemplateBankTurn(difficulty, MovementLength.TWO, MovementTurnDirection.LEFT);
            case FORWARD_2:
                return new MovementTemplateForward(difficulty, MovementLength.TWO);
            case RIGHT_BANK_2:
                return new MovementTemplateBankTurn(difficulty, MovementLength.TWO, MovementTurnDirection.RIGHT);
            case RIGHT_HARD_2:
                return new MovementTemplateHardTurn(difficulty, MovementLength.TWO, MovementTurnDirection.RIGHT);
            case K_TURN_2:
                return new MovementTemplateKoiganTurn(difficulty, MovementLength.TWO);
            case LEFT_SLOOP_2:
                return new MovementTemplateSegnorLoop(difficulty, MovementLength.TWO, MovementTurnDirection.LEFT);
            case RIGHT_SLOOP_2:
                return new MovementTemplateSegnorLoop(difficulty, MovementLength.TWO, MovementTurnDirection.RIGHT);
            case LEFT_TALON_2:
                return new MovementTemplateTalonRoll(difficulty, MovementLength.TWO, MovementTurnDirection.LEFT);
            case RIGHT_TALON_2:
                return new MovementTemplateTalonRoll(difficulty, MovementLength.TWO, MovementTurnDirection.RIGHT);

            case LEFT_HARD_3:
                return new MovementTemplateHardTurn(difficulty, MovementLength.THREE, MovementTurnDirection.LEFT);
            case LEFT_BANK_3:
                return new MovementTemplateBankTurn(difficulty, MovementLength.THREE, MovementTurnDirection.LEFT);
            case FORWARD_3:
                return new MovementTemplateForward(difficulty, MovementLength.THREE);
            case RIGHT_BANK_3:
                return new MovementTemplateBankTurn(difficulty, MovementLength.THREE, MovementTurnDirection.RIGHT);
            case RIGHT_HARD_3:
                return new MovementTemplateHardTurn(difficulty, MovementLength.THREE, MovementTurnDirection.RIGHT);
            case K_TURN_3:
                return new MovementTemplateKoiganTurn(difficulty, MovementLength.THREE);
            case LEFT_SLOOP_3:
                return new MovementTemplateSegnorLoop(difficulty, MovementLength.THREE, MovementTurnDirection.LEFT);
            case RIGHT_SLOOP_3:
                return new MovementTemplateSegnorLoop(difficulty, MovementLength.THREE, MovementTurnDirection.RIGHT);
            case LEFT_TALON_3:
                return new MovementTemplateTalonRoll(difficulty, MovementLength.THREE, MovementTurnDirection.LEFT);
            case RIGHT_TALON_3:
                return new MovementTemplateTalonRoll(difficulty, MovementLength.THREE, MovementTurnDirection.RIGHT);

            case FORWARD_4:
                return new MovementTemplateForward(difficulty, MovementLength.FOUR);
            case K_TURN_4:
                return new MovementTemplateKoiganTurn(difficulty, MovementLength.FOUR);

            case FORWARD_5:
                return new MovementTemplateForward(difficulty, MovementLength.FIVE);
            case K_TURN_5:
                return new MovementTemplateKoiganTurn(difficulty, MovementLength.FIVE);

            default:
                throw new RuntimeException("Invalid movement type " + type.name());
        }
    }
}