package gui;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import rendering.obstacles.ObstacleToken;
import rendering.obstacles.ObstacleType;
import rendering.playarea.PlayArea;

/**
 * Created by Brian on 3/27/2016.
 */
public class ObstacleSelectionPane extends StackPane {

    private PerspectiveCamera camera;
    private Group asteroids;
    private SubScene subScene;

    public ObstacleSelectionPane(int paneDimension) {
        super();
        this.camera = buildCamera();
        this.asteroids = getAsteroidSet(4, 100.0f);
        this.subScene = buildSubScene(camera, asteroids);
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

    private SubScene buildSubScene(Camera camera, Group group) {
        SubScene subScene = new SubScene(group, 600, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.rgb(30, 30, 30));
        subScene.setCamera(camera);
        return subScene;
    }

    private PerspectiveCamera buildCamera() {

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(10);
        camera.setFarClip(500.0);
        camera.setTranslateX(200.0);
        camera.setTranslateY(300.0);
        camera.setTranslateZ(-300.0);
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


    private Group getAsteroidSet(int numPerRow, float spacing) {
        Group asteroidGroup = new Group();
        int row = 0; int column = 0;
        for (ObstacleType type : ObstacleType.values()) {
            //if (type.toString().contains("BASE")) {
                ObstacleToken token = new ObstacleToken(type);
                token.getTransforms().add(new Translate(spacing/2.0f + spacing * column, spacing/2.0f + spacing * row));
                asteroidGroup.getChildren().add(token);
                column++;
                if (column >= numPerRow) {
                    column = 0;
                    row++;
                }
            //}
        }
        return asteroidGroup;
    }
}
