package movement;

import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

/**
 * Created by Brian on 12/17/2015.
 */
public abstract class MovementTemplate extends Path implements MovementTransform {

    private MovementType type;
    private MovementDifficulty difficulty;

    protected static double TEMPLATE_WIDTH = 20.0;

    public MovementTemplate(MovementDifficulty difficulty, MovementType type) {
        this.difficulty = difficulty;
        this.type = type;
    }

    public MovementDifficulty getDifficulty() {
        return difficulty;
    }

    public MovementType getType() {
        return type;
    }

    protected static Color getColorForDifficulty(MovementDifficulty difficulty) {
        switch (difficulty) {
            case GREEN:
                return Color.DARKGREEN;
            case WHITE:
                return Color.ANTIQUEWHITE;
            case RED:
                return Color.DARKRED;
            default:
                throw new RuntimeException("Invalid difficulty " + difficulty.name());
        }
    }
}
