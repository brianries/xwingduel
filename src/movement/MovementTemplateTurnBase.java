package movement;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Created by Brian on 12/19/2015.
 */
public abstract class MovementTemplateTurnBase extends MovementTemplate {

    public MovementTemplateTurnBase(MovementDifficulty difficulty, MovementLength length, MovementTurnDirection direction, MovementTurnType turnType) {
        super(difficulty, getMovementType(turnType, direction, length));
        init(direction, difficulty, length, turnType);
    }

    private static MovementType getMovementType(MovementTurnType turnType, MovementTurnDirection direction, MovementLength length) {
        switch (length) {
            case ONE:
                switch (turnType) {
                    case HARD:
                        return direction == MovementTurnDirection.LEFT ? MovementType.LEFT_HARD_1 : MovementType.RIGHT_HARD_1;
                    case BANK:
                        return direction == MovementTurnDirection.LEFT ? MovementType.LEFT_BANK_1 : MovementType.RIGHT_BANK_1;
                    case SLOOP:
                        return direction == MovementTurnDirection.LEFT ? MovementType.LEFT_SLOOP_1 : MovementType.RIGHT_SLOOP_1;
                    case TALON:
                        return direction == MovementTurnDirection.LEFT ? MovementType.LEFT_TALON_1 : MovementType.RIGHT_TALON_1;
                    default:
                        throw new RuntimeException("No valid turn type for " + turnType.name());
                }
            case TWO:
                switch (turnType) {
                    case HARD:
                        return direction == MovementTurnDirection.LEFT ? MovementType.LEFT_HARD_2 : MovementType.RIGHT_HARD_2;
                    case BANK:
                        return direction == MovementTurnDirection.LEFT ? MovementType.LEFT_BANK_2 : MovementType.RIGHT_BANK_2;
                    case SLOOP:
                        return direction == MovementTurnDirection.LEFT ? MovementType.LEFT_SLOOP_2 : MovementType.RIGHT_SLOOP_2;
                    case TALON:
                        return direction == MovementTurnDirection.LEFT ? MovementType.LEFT_TALON_2 : MovementType.RIGHT_TALON_2;
                    default:
                        throw new RuntimeException("No valid turn type for " + turnType.name());
                }
            case THREE:
                switch (turnType) {
                    case HARD:
                        return direction == MovementTurnDirection.LEFT ? MovementType.LEFT_HARD_3 : MovementType.RIGHT_HARD_3;
                    case BANK:
                        return direction == MovementTurnDirection.LEFT ? MovementType.LEFT_BANK_3 : MovementType.RIGHT_BANK_3;
                    case SLOOP:
                        return direction == MovementTurnDirection.LEFT ? MovementType.LEFT_SLOOP_3 : MovementType.RIGHT_SLOOP_3;
                    case TALON:
                        return direction == MovementTurnDirection.LEFT ? MovementType.LEFT_TALON_3 : MovementType.RIGHT_TALON_3;
                    default:
                        throw new RuntimeException("No valid turn type for " + turnType.name());
                }
            case FOUR:
            case FIVE:
            default:
                throw new RuntimeException("No valid hard turn for length " + length.name());
        }
    }

    private static double[] getRadiiForTurn(MovementTurnType turnType, MovementLength length) {
        switch (turnType) {
            case BANK:
            case SLOOP:
                switch (length) {
                    case ONE:
                        return new double[] {70.0, 90.0};
                    case TWO:
                        return new double[] {120.0, 140.0};
                    case THREE:
                        return new double[] {170.0, 190.0};
                    default:
                        throw new RuntimeException("Invalid length " + length.name() + " for turn type " + turnType.name());
                }
            case HARD:
            case TALON:
                switch (length) {
                    case ONE:
                        return new double[] {25.0, 45.0};
                    case TWO:
                        return new double[] {53.0, 73.0};
                    case THREE:
                        return new double[] {80.0, 100.0};
                    default:
                        throw new RuntimeException("Invalid length " + length.name() + " for turn type " + turnType.name());
                }
            default:
                throw new RuntimeException("Invalid turn type " + turnType.name());
        }
    }

    private static double getAngleInDegrees(MovementTurnType turnType) {
        switch (turnType) {
            case BANK:
            case SLOOP:
                return 45.0;
            case HARD:
            case TALON:
                return 90.0;
            default:
                throw new RuntimeException("Invalid turn type " + turnType.name());
        }
    }

    private void init(MovementTurnDirection direction, MovementDifficulty difficulty, MovementLength length, MovementTurnType turnType) {
        double angleDegrees = getAngleInDegrees(turnType);
        double[] radii = getRadiiForTurn(turnType, length);

        double innerRadius = radii[0];
        double outerRadius = radii[1];
        double centerRadius = (outerRadius + innerRadius) / 2.0;

        double dir = direction == MovementTurnDirection.LEFT ? -1.0 : 1.0;
        double rads = angleDegrees * Math.PI / 180.0;

        double pivotX = 0.0;
        double pivotY = 0 + dir * (innerRadius + MOVEMENT_TEMPLATE_WIDTH /2.0);

        double startX = 0.0;
        double startY = 0.0 + dir * MOVEMENT_TEMPLATE_WIDTH / 2.0;

        double innerX = pivotX + Math.sin(rads) * innerRadius;
        double innerY = pivotY - dir * Math.cos(rads) * innerRadius;

        double centerX = pivotX + Math.sin(rads) * centerRadius;
        double centerY = pivotY - dir * Math.cos(rads) * centerRadius;

        double outerX = pivotX + Math.sin(rads) * outerRadius;
        double outerY = pivotY - dir * Math.cos(rads) * outerRadius;

        double endX = 0.0;
        double endY = 0.0 - dir * MOVEMENT_TEMPLATE_WIDTH / 2.0;

        boolean sweepDirection = (direction == MovementTurnDirection.LEFT) ? false : true;
        this.getElements().add(new MoveTo(startX, startY));
        this.getElements().add(new ArcTo(innerRadius, innerRadius, angleDegrees, innerX, innerY, false, sweepDirection));
        this.getElements().add(new LineTo(outerX, outerY));
        this.getElements().add(new ArcTo(outerRadius, outerRadius, angleDegrees, endX, endY, false, !sweepDirection));
        this.getElements().add(new LineTo(startX, startY));
        this.setFill(getColorForDifficulty(difficulty));
        this.setStroke(Color.WHITE);
        this.setFillRule(FillRule.NON_ZERO);

        Translate translate = new Translate(centerX, centerY);
        Rotate rotate = new Rotate(dir * angleDegrees);
        endTransform.append(translate);
        endTransform.append(rotate);
    }
 }
