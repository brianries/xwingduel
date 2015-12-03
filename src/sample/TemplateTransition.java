package sample;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * Created by Brian on 12/2/2015.
 */
public class TemplateTransition extends Transition {

    private Node[] nodes;

    public TemplateTransition(Duration duration, Node... nodes) {
        this.nodes = nodes;
        setCycleDuration(duration);
    }

    @Override
    protected void interpolate(double frac) {
        Rotate rotate = new Rotate(frac * 360.0, 0, 0);
        for (Node node : nodes) {
            node.getTransforms().setAll(rotate);
        }
    }
}
