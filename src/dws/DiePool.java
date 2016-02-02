package dws;

/**
 * Created by dsayles on 6/10/15.
 */
public class DiePool {
    public static final int RED_DIE = 0;
    public static final int BLUE_DIE = 1;
    public static final int BLACK_DIE = 2;

    public static final int DIE_TYPE_COUNT = 3;
    private int[] antiSquadronDice = new int[DIE_TYPE_COUNT];
    private int[] antiShipDice = new int[DIE_TYPE_COUNT];

    public int[] getSquadronPool() {
        int[] ret = new int[DIE_TYPE_COUNT];
        System.arraycopy(antiSquadronDice, 0, ret, 0, antiSquadronDice.length);
        return ret;
    }

    public int[] getShipPool() {
        int[] ret = new int[DIE_TYPE_COUNT];
        System.arraycopy(antiSquadronDice, 0, ret, 0, antiShipDice.length);
        return ret;
    }

    public void setSquadronPool(int[] pool) {
        if (pool.length == DIE_TYPE_COUNT) {
            System.arraycopy(pool, 0, antiSquadronDice, 0, pool.length);
        }
    }

    public void setSquadronPool(int red, int blue, int black) {
        antiSquadronDice[RED_DIE] = red;
        antiSquadronDice[BLUE_DIE] = blue;
        antiSquadronDice[BLACK_DIE] = black;
    }

    public void setShipPool(int[] pool) {
        if (pool.length == DIE_TYPE_COUNT) {
            System.arraycopy(pool, 0, antiShipDice, 0, pool.length);
        }
    }

    public void setShipPool(int red, int blue, int black) {
        antiShipDice[RED_DIE] = red;
        antiShipDice[BLUE_DIE] = blue;
        antiShipDice[BLACK_DIE] = black;
    }

    public void incrementSquadron(int dieType) {
        antiSquadronDice[dieType]++;
    }

    public void decrementSquadron(int dieType) {
        antiSquadronDice[dieType]--;
    }

    public void incrementShip(int dieType) {
        antiShipDice[dieType]++;
    }

    public void decrementShip(int dieType) {
        antiShipDice[dieType]--;
    }
}
