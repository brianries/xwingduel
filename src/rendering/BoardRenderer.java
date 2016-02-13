package rendering;

import base.Unit;
import javafx.scene.Group;
import rendering.ship.ShipToken;
import state.BoardState;
import state.LocalBoardState;

import java.util.HashMap;

/**
 * Created by Brian on 2/8/2016.
 */
public class BoardRenderer implements LocalBoardState.StateChangeListener {

    // The data model
    private LocalBoardState boardState;

    private HashMap<Unit,ShipToken> shipTokenHashMap;

    private Group root = new Group();
    private Group world = new Group();

    public BoardRenderer(LocalBoardState state) {
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
