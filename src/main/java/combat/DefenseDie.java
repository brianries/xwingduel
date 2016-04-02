package combat;

/**
 * Created by Brian on 2/1/2016.
 */
public class DefenseDie extends Die {

    private static final DieFace[] faces = new DieFace[] {
            DieFace.BLANK,
            DieFace.BLANK,
            DieFace.BLANK,
            DieFace.FOCUS,
            DieFace.FOCUS,
            DieFace.EVADE,
            DieFace.EVADE,
            DieFace.EVADE,
    };

    public DefenseDie(RandomGenerator generator) {
        super(generator, faces);
    }
}
