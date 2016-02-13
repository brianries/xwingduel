package rendering;

import base.Unit;
import javafx.scene.Group;
import rendering.ship.ShipToken;
import state.LocalBoardState;

import java.util.HashMap;

/**
 * Created by Brian on 2/8/2016.
 */
public class BoardScene extends Group implements LocalBoardState.StateChangeListener {

    private LocalBoardState boardState;
    private HashMap<Unit,ShipToken> shipTokenHashMap;

    public BoardScene(LocalBoardState state) {
        super();
        this.boardState = state;
        this.boardState.addStateChangeListener(this);
        this.shipTokenHashMap = new HashMap<>();

    }

    public void renderSecene() {

    }

    @Override
    public void stateChanged() {

    }
}
