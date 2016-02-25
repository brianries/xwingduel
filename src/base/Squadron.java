package base;

import expansions.core.pilots.*;
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
    List<UnitId> unitIdSetByPilotSkill;


    public Squadron(Faction faction) {
        this.faction = faction;
    }

    public void addUnit(Unit unit) {
        squadron.put(unit.getUnitId(), unit);
    }

    public void toggleHasInitiative() {hasInitiative=!hasInitiative;}
    public boolean hasInitiative() {return hasInitiative;}
    public void setPilotSkillOrder() {
        List<UnitId> sortedUnitIdList = new ArrayList<>();
        sortedUnitIdList.addAll(squadron.keySet());
        Collections.sort(sortedUnitIdList);
        unitIdSetByPilotSkill = sortedUnitIdList;
    }


    public boolean containsUnitId(UnitId uid) {
        return squadron.containsKey(uid);
    }

    public Set<UnitId> getActiveUnitIdList() {
        Set<UnitId> activeUnits = new HashSet<>();

        for (UnitId uid : squadron.keySet()) {
            if (uid.unit.isActive()) {
                activeUnits.add(uid);
            }
        }
        return activeUnits;
    }

    public List<UnitId>[] pilotSkillOrderAscending(Squadron altSquadron) {
        List<UnitId>[] fullListAscending = new ArrayList[13];
        for (int i = 0; i <= 12; i++) {
            fullListAscending[i] = new ArrayList<>();
        }

        List<UnitId> initiativeSquadron = (hasInitiative) ? unitIdSetByPilotSkill : altSquadron.unitIdSetByPilotSkill;
        List<UnitId> nonInitiativeSquadron = (hasInitiative) ? altSquadron.unitIdSetByPilotSkill : unitIdSetByPilotSkill;

        for (int i = 0; i <= 12; i++) {
            for (UnitId uid : initiativeSquadron) {
                if (uid.unit.getPilotSkill() == i && uid.unit.isActive()) { fullListAscending[i].add(uid); }
            }
            for (UnitId uid : nonInitiativeSquadron) {
                if (uid.unit.getPilotSkill() == i && uid.unit.isActive()) { fullListAscending[i].add(uid); }
            }
        }
        return fullListAscending;
    }

    public List<UnitId>[] pilotSkillOrderDescending(Squadron altSquadron) {
        List<UnitId>[] fullListDescending = new ArrayList[13];
        for (int i = 0; i <= 12; i++) {
            fullListDescending[i] = new ArrayList<>();
        }

        List<UnitId>[] fullListAscending = pilotSkillOrderAscending(altSquadron);

        for (int i = fullListAscending.length-1, j=0; i >= 0; i--, j++) {
            fullListDescending[j] = fullListAscending[i];
        }

        return fullListDescending;
    }


    public void showUnits() {
        for (Map.Entry<UnitId, Unit> entry : squadron.entrySet()) {
            System.out.println(entry.getKey().uniqueId + " " + entry.getValue().getPilotSkill() + " "
                + entry.getValue().getPilot().name + " " + entry.getValue().getShip().name + " "
                + entry.getKey()
            );
        }
    }

    public static void showUnits(List<UnitId>[] list) {
        int i = 0;
        for (List<UnitId> listu : list) {
            System.out.println("PS: "+ i);
            for (UnitId uid : listu) {
                System.out.println("\t"+uid+" "+uid.unit.getPilotSkill()+" "+uid.unit.getPilot().name+" "+uid.unit.getShip().name);
            }
            i++;
        }
    }

    public static void main(String[] args) {
        Squadron rebels = new Squadron(Faction.REBEL_ALLIANCE);
        rebels.addUnit(new Unit(Faction.REBEL_ALLIANCE, new RookiePilot(), new XWing()));
        Unit test = new Unit(Faction.REBEL_ALLIANCE, new BiggsDarklighter(), new XWing());
        rebels.addUnit(test);
        rebels.addUnit(new Unit(Faction.REBEL_ALLIANCE, new LukeSkywalker(), new XWing()));
//        rebels.toggleHasInitiative();
        rebels.setPilotSkillOrder();

        System.out.println("Rebels:");
        rebels.showUnits();

        Squadron imperial = new Squadron(Faction.GALACTIC_EMPIRE);
        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new AcademyPilot(), new TieFighter()));
        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new AcademyPilot(), new TieFighter()));
        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new AcademyPilot(), new TieFighter()));
        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new AcademyPilot(), new TieFighter()));
        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new DarkCurse(), new TieFighter()));
        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new NightBeast(), new TieFighter()));
        imperial.toggleHasInitiative();
        imperial.setPilotSkillOrder();
        System.out.println("\nEmpire");
        imperial.showUnits();

        List<UnitId>[] ascending = rebels.pilotSkillOrderAscending(imperial);
        System.out.println("\nAscending");
        showUnits(ascending);

        System.out.println("Make Biggs inactive");
        test.toggleActive();

        List<UnitId>[] descending = imperial.pilotSkillOrderDescending(rebels);
        System.out.println("\nDescending");
        showUnits(descending);
    }
}
