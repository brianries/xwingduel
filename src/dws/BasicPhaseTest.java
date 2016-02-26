package dws;

import base.Faction;
import base.Squadron;
import base.Unit;
import expansions.core.pilots.*;
import expansions.core.ships.TieFighter;
import expansions.core.ships.XWing;
import phases.PhaseStateManager;

/**
 * Created by dsayles on 2/24/16.
 */
public class BasicPhaseTest {
    public static void main(String[] args) {
        Squadron rebels = new Squadron(Faction.REBEL_ALLIANCE);
        rebels.addUnit(new Unit(Faction.REBEL_ALLIANCE, new XWing(), new RookiePilot()));
//        Unit test = new Unit(Faction.REBEL_ALLIANCE, new BiggsDarklighter(), new XWing());
//        rebels.addUnit(test);
//        rebels.addUnit(new Unit(Faction.REBEL_ALLIANCE, new LukeSkywalker(), new XWing()));
//        rebels.toggleHasInitiative();
        rebels.setPilotSkillOrder();

        System.out.println("Rebels:");
        rebels.showUnits();

        Squadron imperial = new Squadron(Faction.GALACTIC_EMPIRE);
        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new TieFighter(), new AcademyPilot()));
        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE,  new TieFighter(), new AcademyPilot()));
//        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new AcademyPilot(), new TieFighter()));
//        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new AcademyPilot(), new TieFighter()));
//        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new DarkCurse(), new TieFighter()));
//        imperial.addUnit(new Unit(Faction.GALACTIC_EMPIRE, new NightBeast(), new TieFighter()));
        imperial.toggleHasInitiative();
        imperial.setPilotSkillOrder();
        System.out.println("Imperials:");
        imperial.showUnits();


        PhaseStateManager.setSquadron(imperial);
        PhaseStateManager.setSquadron(rebels);

        PhaseStateManager.cyclePhase();
    }
}
