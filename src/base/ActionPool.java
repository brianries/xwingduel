package base;

import java.util.List;

/**
 * Created by dsayles on 2/7/16.
 */
public class ActionPool {
    List<Action> pool;

    public ActionPool() {}

    public boolean contains(Action action) {
        return pool.contains(action);
    }


}
