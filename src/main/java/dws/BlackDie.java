package dws;

/**
 * Created by dsayles on 5/14/15.
 */
public class BlackDie extends Die {

    @Override
    public void setFaces() {
        faces[0] = DieFace.BLANK;
        faces[1] = DieFace.BLANK;
        faces[2] = DieFace.HIT;
        faces[3] = DieFace.HIT;
        faces[4] = DieFace.HIT;
        faces[5] = DieFace.HIT;
        faces[6] = DieFace.HIT_AND_CRIT;
        faces[7] = DieFace.HIT_AND_CRIT;
    }
}
