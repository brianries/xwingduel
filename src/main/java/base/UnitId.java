package base;

import java.util.Comparator;

/**
 * Created by dsayles on 2/8/16.
 */
public class UnitId implements Comparable<UnitId> {
    Faction faction;
    Unit unit;
    final int uniqueId;
    private static int nextAvailableId = 0;

    public static UnitId createUnitId(Unit unit) {
        return new UnitId(unit);
    }
    private UnitId(Unit unit) {
        this.unit = unit;
        this.faction = unit.getFaction();
        this.uniqueId = nextAvailableId++;
    }

    @Override
    public int compareTo(UnitId o) {
        if (uniqueId == o.uniqueId) return 0;
        if (unit.getPilotSkill() == o.unit.getPilotSkill()) return 0;
        if (unit.getPilotSkill() < o.unit.getPilotSkill()) return -1;
        return 1;
    }
}
