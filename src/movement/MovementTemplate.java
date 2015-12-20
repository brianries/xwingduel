package movement;

import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

import java.awt.geom.AffineTransform;

/**
 * Created by Brian on 12/17/2015.
 */
public abstract class MovementTemplate extends Path implements MovementTransform {

    protected MovementType type;
    protected MovementDifficulty difficulty;
    protected Affine endTransform;

    protected static double MOVEMENT_TEMPLATE_WIDTH = 20.0;

    public MovementTemplate(MovementDifficulty difficulty, MovementType type) {
        this.difficulty = difficulty;
        this.type = type;
        this.endTransform = new Affine();
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
                return Color.LIGHTGRAY;
            case RED:
                return Color.DARKRED;
            default:
                throw new RuntimeException("Invalid difficulty " + difficulty.name());
        }
    }

    @Override
    public Transform getEndTransform() {
        return endTransform;
    }


}
