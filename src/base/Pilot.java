package base;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dsayles on 12/19/15.
 */
public abstract class Pilot {
    protected String name;
    protected int pointCost;
    protected Set<Ship> pilotableShips = new HashSet<>();
    protected Faction faction;
    protected int skill;
    protected boolean unique;

    public Pilot() {
        initialize();
    }

    public abstract void initialize();
    public Faction getFaction() { return this.faction; }
    public void setFaction(Faction faction) { this.faction = faction; }
}
