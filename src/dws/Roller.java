package dws;

import java.util.Random;

/**
 * Created by dsayles on 5/14/15.
 */
public class Roller {
    static Random rand = new Random(System.nanoTime());
    static RedDie redDie = new RedDie();
    static BlackDie blackDie = new BlackDie();
    static BlueDie blueDie = new BlueDie();

    public static DieFace rollRedDie() {
        return roll(redDie);
    }
    public static DieFace rollBlueDie() {
        return roll(blueDie);
    }
    public static DieFace rollBlackDie() {
        return roll(blackDie);
    }

    public static DieFace roll(Die die) {
        return die.result(rand.nextInt(die.faces.length));
    }


    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.println("RedD: "+rollRedDie());
        }
        System.out.println("");
        for (int i = 0; i < 3; i++) {
            System.out.println("BlkD: "+rollBlackDie());
        }
        System.out.println("");
        for (int i = 0; i < 3; i++) {
            System.out.println("BluD: "+rollBlueDie());
        }
    }
}
