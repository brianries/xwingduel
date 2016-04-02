package base;

import phases.Phase;
import phases.PhaseStateManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsayles on 2/7/16.
 */
public class Unit {
    private boolean active=true;
    private UnitId unitId;
    private Faction faction;
    private Ship ship;
    private Pilot pilot;

    private int agility;
    private int hull;
    private int shields;

    private ActionPool actionPool = new ActionPool();
    private List<Upgrade> upgrades = new ArrayList<>();

    public Unit(Faction faction, Ship ship, Pilot pilot) {
        this.faction = faction;
        this.ship = ship;
        this.pilot = pilot;
        this.unitId = UnitRegistry.registerUnit(this);
        this.hull = ship.getHull();
        this.shields = ship.getShields();
    }

    public void registerPhases() {
        PhaseStateManager.registerUnitIdForPhase(Phase.PLANNING, unitId);
        PhaseStateManager.registerUnitIdForPhase(Phase.ACTIVATION, unitId);
        PhaseStateManager.registerUnitIdForPhase(Phase.COMBAT, unitId);
        PhaseStateManager.registerUnitIdForPhase(Phase.END, unitId);
        for (Upgrade upgrade : upgrades) {
            upgrade.registerPhase(unitId);
        }
    }

    public boolean containsAction(Action action) {
        return actionPool.contains(action);
    }

    public void addAction(Action action) {
        actionPool.addAction(action);
    }

    public Faction getFaction() { return faction; }
    public UnitId getUnitId() { return unitId; }
    public int getPilotSkill() { return this.pilot.skill; }
    public Pilot getPilot() { return this.pilot; }
    public Ship getShip() { return this.ship; }
    public void toggleActive() { active = !active; }
    public boolean isActive() { return active; }
    public int getHull() { return this.hull; }
    public int getShields() { return this.shields; }
    public void incrementShields() { this.shields++; }

}
