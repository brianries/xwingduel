package gui;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import movement.MovementDifficulty;
import movement.MovementType;


/**
 * Created by Brian on 8/16/2016.
 */
public class MovementSelectionPane extends StackPane {

    private static int NUM_COLS = 6;
    private static int NUM_ROWS = 5;

    private static int BUTTON_SIZE_PX = 50;
    private static int SPACING_PX = 5;

    public MovementSelectionPane() {
        super();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(SPACING_PX);
        gridPane.setVgap(SPACING_PX);

        MovementType[] types = MovementType.values();
        MovementImageUtil util = new MovementImageUtil();
        MovementDifficulty[] difficulties = MovementDifficulty.values();

        int index = 0;
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                MovementType type = types[index++];
                MovementDifficulty difficulty = difficulties[index % difficulties.length];
                ImageView view = util.getImageView(type, difficulty, BUTTON_SIZE_PX, BUTTON_SIZE_PX);
                Button button = new Button("", view);
                gridPane.add(button, col, row);
            }
        }

        VBox box = new VBox();
        box.getChildren().add(gridPane);

        this.getChildren().add(box);
    }


}
