package base;

/**
 * Created by dsayles on 2/7/16.
 */
public class Unit {
    private boolean active=true;
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

    public UnitId getUnitId() { return unitId; }
    public Faction getFaction() {
        return faction;
    }
    public int getPilotSkill() {
        return this.pilot.skill;
    }
    public Pilot getPilot() { return this.pilot; }
    public Ship getShip() { return this.ship; }
    public void toggleActive() { active = !active; }
    public boolean isActive() { return active; }
    public void addAction(Action action) { actionPool.addAction(action); }
}
