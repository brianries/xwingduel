package base;

import java.io.Serializable;

/**
 * Created by dsayles on 2/16/16.
 */
public interface Upgrade extends Serializable {

    UpgradeType getType();

    int getPointCost();

    void registerPhase(UnitId unitId);
}
