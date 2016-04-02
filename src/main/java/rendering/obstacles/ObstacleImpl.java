package rendering.obstacles;

import base.Unit;
import rendering.movement.MovementTemplate;

/**
 * Created by Brian on 2/13/2016.
 */
public abstract class  ObstacleImpl implements Obstacle {

    private ObstacleType obstacleType;

    public ObstacleImpl(ObstacleType obstacleType) {
        this.obstacleType = obstacleType;
    }

    @Override
    public boolean overlapsMovement(MovementTemplate movementTemplate) {
        return false;
    }

    @Override
    public boolean overlaps(Unit unit) {
        return false;
    }

    @Override
    public boolean obstructs(Unit attacker, Unit defender) {
        return false;
    }
}
