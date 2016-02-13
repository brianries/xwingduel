package gui;

import javafx.geometry.Pos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import rendering.BoardScene;
import rendering.playarea.PlayArea;
import state.LocalBoardState;

/**
 * Created by Brian on 2/13/2016.
 */
public class GameBoardPane extends StackPane {

    private BoardScene boardScene;
    private PerspectiveCamera camera;
    private SubScene subScene;

    public GameBoardPane(LocalBoardState localBoardState) {
        super();
        this.boardScene = new BoardScene(localBoardState);
        this.camera = buildCamera();
        this.subScene = buildSubScene(camera);
        this.getChildren().add(subScene);
        this.setMinSize(0, 0);
        this.setAlignment(subScene, Pos.CENTER);

        // resize the subscene to the size of this window
        subScene.heightProperty().bind(this.heightProperty());
        subScene.widthProperty().bind(this.widthProperty());

        // Ensure we change the field of view for skinny wide or tall windows appropriately
        this.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            double height = this.getHeight();
            setCameraFieldOfView(width > height);
        });
        this.heightProperty().addListener((observable, oldValue, newValue) -> {
            double width = this.getWidth();
            double height = newValue.doubleValue();
            setCameraFieldOfView(width > height);
        });
    }

    private SubScene buildSubScene(PerspectiveCamera camera) {
        SubScene subScene = new SubScene(boardScene, 600, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.rgb(30, 30, 30));
        subScene.setCamera(camera);
        return subScene;
    }

    private PerspectiveCamera buildCamera() {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(100);
        camera.setFarClip(1000.0);
        camera.setTranslateX(PlayArea.BATTLE_GROUND_WIDTH_MM / 2.0);
        camera.setTranslateY(PlayArea.BATTLE_GROUND_WIDTH_MM / 2.0);
        camera.setTranslateZ(-(PlayArea.BATTLE_GROUND_WIDTH_MM / 2.0));
        camera.setFieldOfView(90.0);
        camera.setVerticalFieldOfView(true);
        return camera;
    }

    private void setCameraFieldOfView(boolean widthGreaterThanHeight) {
        if (widthGreaterThanHeight) {
            camera.setVerticalFieldOfView(true);
        }
        else {
            camera.setVerticalFieldOfView(false);
        }
    }

    @Deprecated
    public BoardScene getBoardScene() {
        return boardScene;
    }
}
