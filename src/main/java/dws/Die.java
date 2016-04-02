package dws;

/**
 * Created by dsayles on 5/14/15.
 */
public abstract class Die {
    DieFace[] faces = new DieFace[8];
    public Die() {
        setFaces();
    }
    public abstract void setFaces();
    public DieFace result(int value) { return faces[value]; }
}
