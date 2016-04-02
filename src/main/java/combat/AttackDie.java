package combat;

/**
 * Created by Brian on 2/1/2016.
 */
public class AttackDie extends Die {

    private static final DieFace faces[] = new DieFace[] {
            DieFace.BLANK,
            DieFace.BLANK,
            DieFace.FOCUS,
            DieFace.FOCUS,
            DieFace.HIT,
            DieFace.HIT,
            DieFace.HIT,
            DieFace.CRIT,
    };

    public AttackDie(RandomGenerator generator) {
        super(generator, faces);
    }
}
