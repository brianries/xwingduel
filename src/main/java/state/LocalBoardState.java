package state;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Brian on 2/1/2016.
 */
public class LocalBoardState implements BoardState {

    public interface StateChangeListener {
        void stateChanged();
    }

    private CopyOnWriteArrayList<StateChangeListener> stateChangeListeners;

    public LocalBoardState() {
        stateChangeListeners = new CopyOnWriteArrayList<>();
    }


    public void addStateChangeListener(StateChangeListener listener) {
        stateChangeListeners.add(listener);
    }

    public void removeStateChangeListener(StateChangeListener listener) {
        stateChangeListeners.remove(listener);
    }
}
