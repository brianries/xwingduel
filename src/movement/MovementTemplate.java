package movement;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.shape.*;
import javafx.scene.transform.Transform;

/**
 * Created by Brian on 12/5/2015.
 */
public interface MovementTemplate {

    Transform getEndTransform();

    Node getDisplayToken();


}
