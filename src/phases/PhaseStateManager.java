package phases;

import base.Squadron;
import base.UnitId;
import events.*;

import java.util.List;
import java.util.Set;

/**
 * Created by dsayles on 2/24/16.
 */
public class PhaseStateManager {
    public enum Side {
        LEFT, RIGHT
    }

    static Squadron leftSquadron;
    static Squadron rightSquadron;

    public PhaseStateManager() {}


    public static void setSquadron(Squadron squadron) {
        if (squadron.hasInitiative()) {
            setSquadron(squadron, Side.LEFT);
        } else {
            setSquadron(squadron, Side.RIGHT);
        }
    }

    public static void setSquadron(Squadron squadron, Side side) {
        switch (side) {
            case LEFT: leftSquadron=squadron; break;
            case RIGHT: rightSquadron=squadron; break;
        }
    }

    public static Set<UnitId> getEnemySquadSet(UnitId uid) {
        if (leftSquadron.containsUnitId(uid)) {
            return rightSquadron.getActiveUnitIdList();
        }
        return leftSquadron.getActiveUnitIdList();
    }

    public static void cyclePhase() {
        List<UnitId>[] ascending = leftSquadron.pilotSkillOrderAscending(rightSquadron);
        List<UnitId>[] descending = leftSquadron.pilotSkillOrderDescending(rightSquadron);

        processPhase(Phase.PLANNING, ascending);
        processPhase(Phase.DECLOAK, ascending);
        processPhase(Phase.ACTIVATION, ascending);
        processPhase(Phase.COMBAT, descending);
        processPhase(Phase.END, ascending);
    }

    public static void processPhase(Phase phase, List<UnitId>[] unitIds) {
        for (List<UnitId> units: unitIds) {
            for (UnitId uid: units) {
                switch (phase) {
                    case PLANNING:
                        new PlanningPhaseEvent(uid);
                        break;
                    case DECLOAK:
                        new DecloakPhaseEvent(uid);
                        break;
                    case ACTIVATION:
                        new ActivationPhaseEvent(uid);
                        break;
                    case COMBAT:
                        new CombatPhaseEvent(uid);
                        break;
                    case END:
                        new EndPhaseEvent(uid);
                        break;
                }
            }
        }
    }
}
