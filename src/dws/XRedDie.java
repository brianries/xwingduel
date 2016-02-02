package dws;

/**
 * Created by dsayles on 5/14/15.
 */
public class XRedDie extends Die {

    @Override
    public void setFaces() {
        faces[0] = DieFace.BLANK;
        faces[1] = DieFace.BLANK;
        faces[2] = DieFace.FOCUS;
        faces[3] = DieFace.FOCUS;
        faces[4] = DieFace.HIT;
        faces[5] = DieFace.HIT;
        faces[6] = DieFace.HIT;
        faces[7] = DieFace.CRITICAL;
    }
}
