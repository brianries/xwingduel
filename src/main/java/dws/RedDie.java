package dws;

/**
 * Created by dsayles on 5/14/15.
 */
public class RedDie extends Die {

    @Override
    public void setFaces() {
        faces[0] = DieFace.BLANK;
        faces[1] = DieFace.BLANK;
        faces[2] = DieFace.CRITICAL;
        faces[3] = DieFace.CRITICAL;
        faces[4] = DieFace.ACCURACY;
        faces[5] = DieFace.HIT;
        faces[6] = DieFace.HIT;
        faces[7] = DieFace.DOUBLE_HIT;
    }
}
