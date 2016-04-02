package base;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dsayles on 2/8/16.
 */
public class UnitRegistry {
    private static Map<UnitId, Unit> _instance = new HashMap<>();
    public static UnitId registerUnit(Unit unit) {
        UnitId unitId = UnitId.createUnitId(unit);
        if (!_instance.containsKey(unitId)) {
            _instance.put(unitId, unit);
        }
        return unitId;
    }

    @Nullable
    public static Unit getUnit(UnitId id) {
        if (_instance.containsKey(id)) {
            return _instance.get(id);
        }
        return null;
    }
}
