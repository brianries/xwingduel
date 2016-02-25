package phases;

import base.actions.TargetLock;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dsayles on 2/24/16.
 */
public class TargetLockManager {
    static Set<TargetLock> targetLocks = new HashSet<>();

    public static boolean registerTargetLock(TargetLock tl) {
        if (targetLocks.contains(tl)) {
            return false;
        }
        targetLocks.add(tl);
        return true;
    }
}
