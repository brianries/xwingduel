package dws;

import java.util.Random;

/**
 * Created by dsayles on 5/14/15.
 */
public class Roller {
    static long seed = 544321211l;
//    static Random rand = new Random(System.nanoTime());
    static Random rand = new Random(seed);
    static RedDie redDie = new RedDie();
    static BlackDie blackDie = new BlackDie();
    static BlueDie blueDie = new BlueDie();
    static XRedDie xRedDie = new XRedDie();
    static XGreenDie xGreenDie = new XGreenDie();

    public static DieFace rollRedDie() {
        return roll(redDie);
    }
    public static DieFace rollBlueDie() {
        return roll(blueDie);
    }
    public static DieFace rollBlackDie() {
        return roll(blackDie);
    }

    public static DieFace rollXRedDie() { return roll(xRedDie);}
    public static DieFace rollXGreenDie() { return roll(xGreenDie);}

    public static DieFace roll(Die die) {
        return die.result(rand.nextInt(die.faces.length));
    }


    public static void main(String[] args) {
//        for (int i = 0; i < 3; i++) {
//            System.out.println("RedD: "+rollRedDie());
//        }
//        System.out.println("");
//        for (int i = 0; i < 3; i++) {
//            System.out.println("BlkD: "+rollBlackDie());
//        }
//        System.out.println("");
//        for (int i = 0; i < 3; i++) {
//            System.out.println("BluD: "+rollBlueDie());
//        }


        System.out.println("");
        for (int i = 0; i < 4; i++) {
            System.out.println("XRed: "+rollXRedDie());
        }
        System.out.println("");
        for (int i = 0; i < 5; i++) {
            System.out.println("XGrn: "+rollXGreenDie());
        }
    }
}
