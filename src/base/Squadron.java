package base;

import expansions.core.pilots.AcademyPilot;
import expansions.core.pilots.RookiePilot;
import expansions.core.ships.TieFighter;
import expansions.core.ships.XWing;

import java.util.*;

/**
 * Created by dsayles on 2/7/16.
 */
public class Squadron {
    boolean hasInitiative = false;
    Faction faction;
    Map<UnitId, Unit> squadron = new HashMap<>();
    Set<UnitId> unitIdSetByPilotSkill;


    public Squadron(Faction faction) {
        this.faction = faction;
    }

    public void addUnit(Unit unit) {
        squadron.put(unit.getUnitId(), unit);
    }


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


    public void showUnits() {
        for (Map.Entry<UnitId, Unit> entry : squadron.entrySet()) {
            System.out.println(entry.getKey().uniqueId + " " + entry.getValue().getPilotSkill() + " "
                + entry.getValue().getPilot().name + " " + entry.getValue().getShip().name
            );
        }
    }

    public static void main(String[] args) {
        Squadron rebels = new Squadron(Faction.REBEL_ALLIANCE);
        rebels.addUnit(new Unit(Faction.REBEL_ALLIANCE, new RookiePilot(), new XWing()));
        rebels.setPilotSkillOrder();
        System.out.println("Rebels:");
        rebels.showUnits();

        Squadron imperial = new Squadron(Faction.GALACTIC_EMPIRE);
        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new AcademyPilot(), new TieFighter()));
        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new AcademyPilot(), new TieFighter()));
        imperial.setPilotSkillOrder();
        System.out.println("\nEmpire");
        imperial.showUnits();


    }
}
