package base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsayles on 2/7/16.
 */
public class ActionPool {
    List<Action> pool = new ArrayList<>();

    public ActionPool() {}

    public boolean contains(Action action) {
        return pool.contains(action);
    }
    public void addAction(Action action) { pool.add(action); }
}
