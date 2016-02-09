package base;

/**
 * Created by dsayles on 2/8/16.
 */
public class UnitId {
    Faction faction;
    Unit unit;
    final int uniqueId;
    private int nextAvailableId = 0;

    public static UnitId createUnitId(Unit unit) {
        return new UnitId(unit);
    }
    private UnitId(Unit unit) {
        this.unit = unit;
        this.faction = unit.getFaction();
        this.uniqueId = nextAvailableId++;
    }
}
