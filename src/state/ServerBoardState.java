package state;

import base.*;
import network.update.UpdateMessage;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Brian on 2/8/2016.
 */
public class ServerBoardState implements BoardState {

    private HashMap<UnitId, Unit> playerOneUnitMap;
    private HashMap<UnitId, Unit> playerTwoUnitMap;

    private CopyOnWriteArrayList<UpdateListener> updateListeners;

    public interface UpdateListener {
        void handleUpdate(UpdateMessage update);
    }

    public ServerBoardState() {
        this.playerOneUnitMap = new HashMap<>();
        this.playerTwoUnitMap = new HashMap<>();
        this.updateListeners = new CopyOnWriteArrayList<>();
    }

    public void addShip(Player player, Faction faction, Ship ship, Pilot pilot) {
        Unit unit = new Unit(faction, ship, pilot);
        if (player == Player.PLAYER_ONE) {
            playerOneUnitMap.put(unit.getUnitId(), unit);
        }
        else {
            playerTwoUnitMap.put(unit.getUnitId(), unit);
        }
        handleUpdateEvent(new UpdateMessage("Added new ship to board"));
    }

    public synchronized void handleUpdateEvent(UpdateMessage update) {
        for (UpdateListener listener : updateListeners) {
            listener.handleUpdate(update);
        }
    }

    public void addUpdateListener(UpdateListener listener) {
        this.updateListeners.add(listener);
    }

    public void removeUpdateListener(UpdateListener listener) {
        this.updateListeners.remove(listener);
    }
}
