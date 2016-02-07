package base;

/**
 * Created by dsayles on 2/7/16.
 */
public class RequiredAction {
    private Action requiredAction;
    public RequiredAction(Action requiredAction) {
        this.requiredAction = requiredAction;
    }

    public boolean containsRequiredAction(Unit unit, Action action) {
        return unit.containsAction(action);
    }
}
