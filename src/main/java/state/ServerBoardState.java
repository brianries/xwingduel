package state;

import base.*;
import network.message.ServerCommands;
import rendering.obstacles.Obstacle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Brian on 2/8/2016.
 */
public class ServerBoardState implements BoardState {

    private ArrayList<Obstacle> obstacleList;
    private HashMap<UnitId, Unit> playerOneUnitMap;
    private HashMap<UnitId, Unit> playerTwoUnitMap;

    private CopyOnWriteArrayList<UpdateListener> updateListeners;

    public interface UpdateListener {
        void handleUpdate(ServerCommands.UpdateBoardState update);
    }

    public ServerBoardState() {
        this.obstacleList = new ArrayList<>();
        this.playerOneUnitMap = new HashMap<>();
        this.playerTwoUnitMap = new HashMap<>();
        this.updateListeners = new CopyOnWriteArrayList<>();
    }

    public void addSquadron(Player player, Faction faction, ArrayList<UnitSubmission> unitSubmissions) {
        for (UnitSubmission unitSubmission : unitSubmissions) {
            addShip(player, faction, unitSubmission.getShip(), unitSubmission.getPilot());
        }
    }

    private void addShip(Player player, Faction faction, Ship ship, Pilot pilot) {
        Unit unit = new Unit(faction, ship, pilot);
        if (player == Player.PLAYER_ONE) {
            playerOneUnitMap.put(unit.getUnitId(), unit);
        } else {
            playerTwoUnitMap.put(unit.getUnitId(), unit);
        }
        ServerCommands.UpdateBoardState updateMsg = ServerCommands.UpdateBoardState.newBuilder()
                // Set fields here
                .build();

        /*
        ServerCommands.BaseCommand baseCommand = ServerCommands.BaseCommand.newBuilder()
                .setType(ServerCommands.BaseCommand.Type.UPDATE_BOARD_STATE)
                .setMessageId(0) // TODO: keep an internal, consistent counter for message ids
                .setUpdateBoardState(updateMsg)
                .build();*/

        handleUpdateEvent(updateMsg);
    }

    public synchronized void handleUpdateEvent(ServerCommands.UpdateBoardState update) {
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
