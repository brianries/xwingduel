package network.message.player.command;

import base.Faction;
import base.Pilot;
import base.Player;
import base.Ship;
import network.message.player.PlayerCommand;

/**
 * Created by Brian on 2/15/2016.
 */
public class PlaceShipCommand extends PlayerCommand {

    private Player player;
    private Faction faction;
    private Ship ship;
    private Pilot pilot;

    public PlaceShipCommand(Player player, Faction faction, Ship ship, Pilot pilot) {
        this.player = player;
        this.faction = faction;
        this.ship = ship;
        this.pilot = pilot;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    @Override
    public Type getMessageType() {
        return Type.PLACE_SHIP;
    }
}
