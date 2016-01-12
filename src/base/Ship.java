package base;

import java.util.EnumSet;

/**
 * Created by dsayles on 12/19/15.
 */
public abstract class Ship {
    Pilot pilot;
    Faction faction;
    int attack;
    int agility;
    int hull;
    int shields;
    EnumSet<Actions> actions;

    public Ship() {}

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
        setFaction(pilot.getFaction());
    }
    public Pilot getPilot() { return this.pilot; }

    private void setFaction(Faction faction) { this.faction = faction; }
    public Faction getFaction() { return this.faction; }

    public void setAttack(int attack) { this.attack = attack; }
    public int getAttack() { return this.attack; }
    public void setAgility(int agility) { this.agility = agility; }
    public int getAgility() { return this.agility; }
    public void setHull(int hull) { this.hull = hull; }
    public int getHull() { return this.hull; }
    public void setShields(int shields) { this.shields = shields; }
    public int getShields() { return this.shields; }

    public void setActions(EnumSet<Actions> actions) { this.actions = actions; }
}
