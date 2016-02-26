package state;

import base.*;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Brian on 2/8/2016.
 */
public class ServerBoardState implements BoardState {

    private HashMap<UnitId, Unit> playerOneUnitMap;
    private HashMap<UnitId, Unit> playerTwoUnitMap;

    private CopyOnWriteArrayList<UpdateListener> updateListeners;

    public static class Update {

    }

    public interface UpdateListener {
        void handleUpdate(Update update);
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
        handleUpdateEvent(new Update());
    }

    public synchronized void handleUpdateEvent(Update update) {
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
