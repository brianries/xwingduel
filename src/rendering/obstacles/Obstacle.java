package rendering.obstacles;

import base.Unit;

/**
 * Created by Brian on 2/13/2016.
 */
public interface Obstacle {
    boolean obstructs(Unit attacker, Unit defender);
}
