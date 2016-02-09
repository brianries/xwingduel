package base;

/**
 * Created by dsayles on 2/7/16.
 */
public class Unit {
    private UnitId unitId;
    private Faction faction;
    private Pilot pilot;
    private Ship ship;

    private ActionPool actionPool;

    public Unit(Faction faction, Pilot pilot, Ship ship) {
        this.faction = faction;
        this.pilot = pilot;
        this.ship = ship;
        this.unitId = UnitRegistry.registerUnit(this);
    }

    public boolean containsAction(Action action) {
        return actionPool.contains(action);
    }

    public Faction getFaction() {
        return faction;
    }
}
