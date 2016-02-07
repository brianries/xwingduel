package base;

/**
 * Created by dsayles on 2/7/16.
 */
public class Unit {
    private ActionPool actionPool;

    public boolean containsAction(Action action) {
        return actionPool.contains(action);
    }
}
