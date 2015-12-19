package movement;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Created by Brian on 12/5/2015.
 */
public class MovementTemplateFactory {

    public static double MOVEMENT_TEMPLATE_WIDTH_MM = 20.0;

    // Firing arc
    //range in mm: 100.0, 200.0, 300.0

    // Forward movement
    //size in mm: 40.0, 80.0, 120.0, 160.0, 200.0

    //Turn inside radii in mm: 25.0, 53.0, 80.0
    //Turn outside radii in mm: 45.0, 73.0, 100.0

    //Bank inside radii in mm: 70.0, 120.0, 170.0
    //Bank outside radii in mm: 90.0, 140.0, 190.0

    public MovementTemplateFactory() {

    }



    public static Path constructForwardTemplate(MovementDifficulty difficulty, double length) {
        Path movementToken = new Path();
        movementToken.getElements().add(new MoveTo(0 - MOVEMENT_TEMPLATE_WIDTH_MM/2.0, 0));
        movementToken.getElements().add(new LineTo(0 - MOVEMENT_TEMPLATE_WIDTH_MM/2.0, length));
        movementToken.getElements().add(new LineTo(0 + MOVEMENT_TEMPLATE_WIDTH_MM/2.0, length));
        movementToken.getElements().add(new LineTo(0 + MOVEMENT_TEMPLATE_WIDTH_MM/2.0, 0));
        movementToken.setFill(getColor(difficulty));
        movementToken.setStroke(getColor(difficulty));
        movementToken.setFillRule(FillRule.NON_ZERO);
        return movementToken;
    }

    public static Path constructMovementToken(MovementTurnDirection turnDirection, MovementDifficulty difficulty, double innerRadius, double outerRadius, double angleDegrees) {
        Path movementToken = new Path();

        double direction = (turnDirection == MovementTurnDirection.LEFT) ? 1.0 : -1.0;

        double rads = angleDegrees * Math.PI / 180.0;

        double width = outerRadius - innerRadius;
        double centerRadius = outerRadius - innerRadius;

        double pivotX = 0 - direction * (innerRadius + width/2.0);
        double pivotY = 0.0;

        double startX = 0.0 - direction * (width / 2.0);
        double startY = 0.0;

        double innerX = pivotX + direction * (Math.cos(rads) * innerRadius);
        double innerY = pivotY + direction * (Math.sin(rads) * innerRadius);

        double outerX = pivotX + direction * (Math.cos(rads) * outerRadius);
        double outerY = pivotY + direction * (Math.sin(rads) * outerRadius);

        double centerX = pivotX + direction * (Math.cos(rads) * centerRadius);
        double centerY = pivotY + direction * (Math.sin(rads) * centerRadius);

        double endX = 0.0 + direction * (width / 2.0);
        double endY = 0.0;

        movementToken.getElements().add(new MoveTo(startX, startY));
        movementToken.getElements().add(new ArcTo(innerRadius, innerRadius, angleDegrees, innerX, innerY, false, true));
        movementToken.getElements().add(new LineTo(outerX, outerY));
        movementToken.getElements().add(new ArcTo(outerRadius, outerRadius, angleDegrees, endX, endY, false, false));
        movementToken.getElements().add(new LineTo(startX, startY));
        movementToken.setFill(getColor(difficulty));
        movementToken.setStroke(getColor(difficulty));
        movementToken.setFillRule(FillRule.NON_ZERO);
        return movementToken;

        //return new MovementTemplateHardTurnBase(movementTemplate, null, difficulty);
    }

    private static Color getColor(MovementDifficulty difficulty) {
        switch (difficulty) {
            case RED:
                return Color.DARKRED;
            case WHITE:
                return Color.WHITE;
            case GREEN:
                return Color.DARKGREEN;
            default:
                throw new RuntimeException("No color for difficult " + difficulty + "found!");
        }
    }

    public MovementTransform constructTemplate(MovementType type, MovementDifficulty difficulty) {
        switch (type) {
            case STOP:
            case LEFT_HARD_1:
            case LEFT_BANK_1:
            case FORWARD_1:
            case RIGHT_BANK_1:
            case RIGHT_HARD_1:
            case K_TURN_1:
            case LEFT_SLOOP_1:
            case RIGHT_SLOOP_1:
            case LEFT_TALON_1:
            case RIGHT_TALON_1:
            case LEFT_HARD_2:
            case LEFT_BANK_2:
            case FORWARD_2:
            case RIGHT_BANK_2:
            case RIGHT_HARD_2:
            case K_TURN_2:
            case LEFT_SLOOP_2:
            case RIGHT_SLOOP_2:
            case LEFT_TALON_2:
            case RIGHT_TALON_2:
            case LEFT_HARD_3:
            case LEFT_BANK_3:
            case FORWARD_3:
            case RIGHT_BANK_3:
            case RIGHT_HARD_3:
            case K_TURN_3:
            case LEFT_SLOOP_3:
            case RIGHT_SLOOP_3:
            case LEFT_TALON_3:
            case RIGHT_TALON_3:
            case FORWARD_4:
            case K_TURN_4:
            case FORWARD_5:
            case K_TURN_5:
            default:
                throw new RuntimeException("Movement type " + type + " not recognized");
        }
    };
}