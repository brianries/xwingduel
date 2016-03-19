package network.message.player.command;

import base.Faction;
import base.UnitSubmission;
import network.message.player.PlayerCommand;
import rendering.obstacles.ObstacleType;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Brian on 3/19/2016.
 */
public class AddSquadronCommand extends PlayerCommand {

    private Faction faction;
    private ArrayList<ObstacleType> selectedObstacles;
    private ArrayList<UnitSubmission> unitSubmissions;

    public AddSquadronCommand(Faction faction, ObstacleType obstacles[], UnitSubmission... unitSubmissions) {
        this.faction = faction;
        this.selectedObstacles = new ArrayList<>(Arrays.asList(obstacles));
        this.unitSubmissions = new ArrayList<>(Arrays.asList(unitSubmissions));
    }

    public Faction getFaction() {
        return faction;
    }

    public ArrayList<ObstacleType> getSelectedObstacles() {
        return selectedObstacles;
    }

    public ArrayList<UnitSubmission> getUnitSubmissions() {
        return unitSubmissions;
    }

    @Override
    public Type getMessageType() {
        return Type.ADD_SQUADRON;
    }
}
