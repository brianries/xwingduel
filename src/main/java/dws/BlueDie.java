package dws;

/**
 * Created by dsayles on 5/14/15.
 */
public class BlueDie extends Die {

    @Override
    public void setFaces() {
        faces[0] = DieFace.CRITICAL;
        faces[1] = DieFace.CRITICAL;
        faces[2] = DieFace.ACCURACY;
        faces[3] = DieFace.ACCURACY;
        faces[4] = DieFace.HIT;
        faces[5] = DieFace.HIT;
        faces[6] = DieFace.HIT;
        faces[7] = DieFace.HIT;
    }
}
