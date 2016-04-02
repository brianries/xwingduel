package combat;

import java.util.Random;

/**
 * Created by Brian on 2/1/2016.
 */
public class RandomGenerator {

    private long seed;
    private Random random;

    public RandomGenerator() {
        this(System.nanoTime());
    }

    public RandomGenerator(long seed) {
        this.seed = seed;
        random = new Random(seed);
    }

    // Returns [0.0-1.0]
    public double nextDouble() {
        return random.nextDouble();
    }

    // Returns [0-bound)  bound is not inclusive
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public long getSeed() {
        return seed;
    }
}
