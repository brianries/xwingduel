package phases;

import base.Squadron;
import base.UnitId;
import events.*;

import java.util.*;

/**
 * Created by dsayles on 2/24/16.
 */
public class PhaseStateManager {
    static Map<Phase, Set<UnitId>> phaseSetMap = new HashMap<>();

    static {
        for (Phase phase : Phase.values()) {
            phaseSetMap.put(phase, new HashSet<>());
        }
    }


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

        // Basic phases, that all ships use...
        // sub phases are in each of them
        processPhase(Phase.PLANNING, ascending);
        processPhase(Phase.DECLOAK, ascending);
        processPhase(Phase.ACTIVATION, ascending);
        processPhase(Phase.COMBAT, descending);
        processPhase(Phase.END, ascending);
    }

    public static boolean phaseContainsUnitId(Phase phase, UnitId unitId) {
        return phaseSetMap.get(phase).contains(unitId);
    }

    public static void processPhase(Phase phase, List<UnitId>[] unitIds) {
        for (List<UnitId> units: unitIds) {
            for (UnitId uid: units) {
                switch (phase) {
                    case PLANNING:
                        new PlanningPhaseEvent(uid);
                        break;
                    case DECLOAK:
                        if (phaseSetMap.get(Phase.DECLOAK).contains(uid)) {
                            new DecloakPhaseEvent(uid);
                        }
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
                    case PRE_REVEAL_MANEUVER:
                        if (phaseSetMap.get(Phase.PRE_REVEAL_MANEUVER).contains(uid)) {
                            new PreRevealManeuverEvent(uid);
                        }
                        break;
                    case PRE_MANEUVER:
                        if (phaseSetMap.get(Phase.PRE_MANEUVER).contains(uid)) {
                            new PreManeuverEvent(uid);
                        }
                        break;
                    case PRE_SELECT_ACTION:
                        if (phaseSetMap.get(Phase.PRE_SELECT_ACTION).contains(uid)) {
                            new PreSelectActionEvent(uid);
                        }
                        break;
                    case REVEAL_MANEUVER:
                        if (phaseSetMap.get(Phase.REVEAL_MANEUVER).contains(uid)) {
                            new RevealManeuverEvent(uid);
                        }
                        break;
                    case MANEUVER:
                        if (phaseSetMap.get(Phase.MANEUVER).contains(uid)) {
                            new ManeuverEvent(uid);
                        }
                        break;
                    case SELECT_ACTION:
                        if (phaseSetMap.get(Phase.SELECT_ACTION).contains(uid)) {
                            new SelectActionEvent(uid);
                        }
                        break;
                    case POST_REVEAL_MANEUVER:
                        if (phaseSetMap.get(Phase.POST_REVEAL_MANEUVER).contains(uid)) {
                            new PostRevealManeuverEvent(uid);
                        }
                        break;
                    case POST_MANEUVER:
                        if (phaseSetMap.get(Phase.POST_MANEUVER).contains(uid)) {
                            new PreManeuverEvent(uid);
                        }
                        break;
                    case POST_SELECT_ACTION:
                        if (phaseSetMap.get(Phase.POST_SELECT_ACTION).contains(uid)) {
                            new PostSelectActionEvent(uid);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void registerUnitIdForPhase(Phase phase, UnitId uid) {
        phaseSetMap.get(phase).add(uid);
    }
}
