package rendering.obstacles;

import base.Unit;

/**
 * Created by Brian on 2/13/2016.
 */
public abstract class ObstacleImpl implements Obstacle {

    public ObstacleImpl() { }

    @Override
    public boolean obstructs(Unit attacker, Unit defender) {
        return false;
    }
}
