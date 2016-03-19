package base;

import java.io.Serializable;

/**
 * Created by Brian on 3/19/2016.
 */
public class UnitSubmission implements Serializable {
    private Ship ship;
    private Pilot pilot;
    private Upgrade[] upgrades;

    // Separate Ship and Pilot since some Pilots are reused across ships
    public UnitSubmission(Ship ship, Pilot pilot, Upgrade... upgrades) {
        this.ship = ship;
        this.pilot = pilot;
        this.upgrades = upgrades;
    }

    public Ship getShip() {
        return ship;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public Upgrade[] getUpgrades() {
        return upgrades;
    }
}
