package combat;

/**
 * Created by Brian on 2/1/2016.
 */
public abstract class Die {

    private final DieFace[] faces;
    private RandomGenerator generator;
    private DieFace faceUp;

    public Die(RandomGenerator generator, DieFace[] faces) {
        this.faces = faces;
        this.generator = generator;
    }

    void roll() {
        faceUp = faces[generator.nextInt(faces.length)];
    }

    DieFace getFace() {
        return faceUp;
    }
}
