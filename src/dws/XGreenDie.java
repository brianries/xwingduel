package dws;

/**
 * Created by dsayles on 5/14/15.
 */
public class XGreenDie extends Die {

    @Override
    public void setFaces() {
        faces[0] = DieFace.EVADE;
        faces[1] = DieFace.EVADE;
        faces[2] = DieFace.EVADE;
        faces[3] = DieFace.FOCUS;
        faces[4] = DieFace.FOCUS;
        faces[5] = DieFace.BLANK;
        faces[6] = DieFace.BLANK;
        faces[7] = DieFace.BLANK;
    }
}
