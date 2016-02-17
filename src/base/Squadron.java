package base;

import java.util.*;

/**
 * Created by dsayles on 2/7/16.
 */
public class Squadron {
    boolean hasInitiative = false;
    Map<UnitId, Unit> squadron = new HashMap<>();
    Set<UnitId> unitIdSetByPilotSkill;

    public void setPilotSkillOrder() {
        SortedSet<UnitId> sortedUnitIdSet = new TreeSet<UnitId>();
        sortedUnitIdSet.addAll(squadron.keySet());
        unitIdSetByPilotSkill = sortedUnitIdSet;
    }


    public List<List<Unit>> pilotSkillOrderAscending(Squadron altSquadron) {
        List<List<Unit>> fullListAscending = new ArrayList<>();

        Set<Map.Entry<UnitId, Unit>> initiativeSquadronSet;
        Set<Map.Entry<UnitId, Unit>> nonInitiativeSquadronSet;

        initiativeSquadronSet = hasInitiative ? squadron.entrySet() : altSquadron.squadron.entrySet();
        nonInitiativeSquadronSet = hasInitiative ? altSquadron.squadron.entrySet() : squadron.entrySet();



        return fullListAscending;
    }

    public List<List<Unit>> pilotSkillOrderDescending(Squadron altSquadron) {
        List<List<Unit>> fullListDescending = new ArrayList<>();



        return fullListDescending;
    }




}
