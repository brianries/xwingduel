package rendering.playarea;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Created by Brian on 2/13/2016.
 */
public class PlayArea extends Rectangle {

    public static final double BATTLE_GROUND_WIDTH_MM = 914.4;

    public PlayArea() {
        super(BATTLE_GROUND_WIDTH_MM, BATTLE_GROUND_WIDTH_MM);
        Image texture = new Image("file:resources/starfield.jpg");
        ImagePattern imagePattern = new ImagePattern(texture);
        this.setFill(imagePattern);
    }
}
