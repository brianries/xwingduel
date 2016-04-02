package rendering.obstacles;

import base.Unit;
import rendering.movement.MovementTemplate;

import java.io.Serializable;

/**
 * Created by Brian on 2/13/2016.
 */
public interface Obstacle {

    boolean overlapsMovement(MovementTemplate movementTemplate);

    boolean overlaps(Unit unit);

    boolean obstructs(Unit attacker, Unit defender);
}
