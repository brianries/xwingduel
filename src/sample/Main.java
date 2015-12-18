package sample;

import com.sun.javafx.fxml.builder.TriangleMeshBuilder;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import ship.FiringArc;
import ship.ShipSize;
import ship.ShipToken;


public class Main extends Application {

    public static double SHIP_TEMPLATE_WIDTH = 40.0;
    private BooleanProperty firingArcVisibile = new SimpleBooleanProperty(true);

    private  TemplateTransition templateTransition;

    private Path path;
    private double x1, y1;

    public Parent createContent(Stage stage) throws Exception {

        final ShipToken shipToken = new ShipToken("resources/ywing.jpg", ShipSize.SMALL);
        MovementTemplate movementTemplate = getHardTurnTemplate(80.0, 100.0, 45.0);
        final FiringArc firingArc = new FiringArc(ShipSize.SMALL);

      //  Group ship = new Group();
     //   ship.getChildren().add(shipToken);
      //  ship.getChildren().add(firingArc);

        // TO DO -- look into this one!
        //ship.contains(0, 0);



        firingArc.translateXProperty().bind(shipToken.translateXProperty());
        firingArc.translateYProperty().bind(shipToken.translateYProperty());
        movementTemplate.movementTemplate.translateXProperty().bind(shipToken.translateXProperty());
        movementTemplate.movementTemplate.translateYProperty().bind(shipToken.translateYProperty());
        shipToken.setTranslateX(200);
        shipToken.setTranslateY(200);




        /*
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setPath(movementTemplate.movementPath);
        pathTransition.setNode(ship);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
        */

        templateTransition = new TemplateTransition(Duration.millis(4000), shipToken, firingArc, movementTemplate.movementTemplate);
        //templateTransition.setPath(movementTemplate.movementPath);
        //templateTransition.setNode(ship);
        //templateTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        templateTransition.setCycleCount(1);
        templateTransition.setAutoReverse(false);
        templateTransition.play();

        // Identity matrix
       // Affine transformMatrix = new Affine();
      //  ship.getTransforms().setAll(transformMatrix);

        /*
        final KeyValue kvx = new KeyValue(ship.translateXProperty(), 500);
        final KeyValue kvy = new KeyValue(ship.translateYProperty(), 500);
        final KeyFrame kfx = new KeyFrame(Duration.millis(1000), kvx);
        final KeyFrame kfy = new KeyFrame(Duration.millis(1000), kvy);
        timeline.getKeyFrames().add(kfx);
        timeline.getKeyFrames().add(kfy);
        timeline.play();              */

        // Build the Scene Graph
        Group root = new Group();
        root.getChildren().add(movementTemplate.movementTemplate);
        root.getChildren().add(firingArc);
        root.getChildren().add(shipToken);

        root.setOnMousePressed(event -> {
            if (event.getTarget() instanceof Rectangle) {
                ((Rectangle) event.getTarget()).setStroke(Color.YELLOW);
            }
        });

        root.setOnMouseReleased(event -> {
            if (event.getTarget() instanceof Rectangle) {
                ((Rectangle) event.getTarget()).setStroke(Color.GRAY);
            }
        });



        root.addEventHandler(MouseEvent.ANY, e -> {
            if(e.getTarget() instanceof Node ) {
                System.out.println("Mouse Event = " + e.getEventType());
                Node node = (Node) e.getTarget();
                if (e.getEventType() == MouseEvent.MOUSE_ENTERED_TARGET) {
                    if (!node.translateXProperty().isBound()) {
                        node.setEffect(new DropShadow(20, Color.WHITESMOKE));
                    }
                }
                else if (e.getEventType() == MouseEvent.MOUSE_EXITED_TARGET) {
                    node.setEffect(null);
                }
                else if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    x1 = e.getX();
                    y1 = e.getY();
                }
                else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    // translate node
                    if (!node.translateXProperty().isBound()) {
                        node.setTranslateX(e.getX() - x1 + node.getTranslateX());
                    }
                    if (!node.translateYProperty().isBound()) {
                        node.setTranslateY(e.getY() - y1 + node.getTranslateY());
                    }
                    x1 = e.getX();
                    y1 = e.getY();
                }
                else if (e.getButton() == MouseButton.SECONDARY) {
                    // right-click over the path to move it to its original position
                    if (!node.translateXProperty().isBound()) {
                        node.setTranslateX(0);
                        node.setTranslateY(0);
                    }
                }
            }
        });


        SubScene scene = createScene(root, stage);

        Group group = new Group();
        group.getChildren().add(scene);
        return group;
    }


    private SubScene createScene(Group rootGroup, Stage stage) {
        Image texture = new Image("file:resources/starfield.jpg");
        ImagePattern imagePattern = new ImagePattern(texture);

        ParallelCamera parallelCamera = new ParallelCamera();

        SubScene subScene = new SubScene(rootGroup, 200, 200, true, null);
        subScene.setFill(imagePattern);
        subScene.setCamera(parallelCamera);
        subScene.heightProperty().bind(stage.heightProperty());
        subScene.widthProperty().bind(stage.widthProperty());

        return subScene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(true);
        Scene scene = new Scene(createContent(primaryStage));

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    templateTransition.play();
                    break;
                case F:
                    firingArcVisibile.set(!firingArcVisibile.get());
                    break;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static class MovementTemplate {
        Path movementPath;
        Path movementTemplate;

        public MovementTemplate(Path movementPath, Path movementTemplate) {
            this.movementPath = movementPath;
            this.movementTemplate = movementTemplate;
        }
    }

    public MovementTemplate getHardTurnTemplate(double innerRadius, double outerRadius, double angleDegrees) {
        Path movementPath = new Path();
        Path movementTemplate = new Path();

        double rads = angleDegrees * Math.PI / 180.0;

        double width = outerRadius - innerRadius;
        double centerRadius = outerRadius - innerRadius;

        double pivotX = 0 - innerRadius - width/2.0;
        double pivotY = 0.0;

        double startX = 0.0 - width / 2.0;
        double startY = 0.0;

        double innerX = pivotX + Math.cos(rads) * innerRadius;
        double innerY = pivotY + Math.sin(rads) * innerRadius;

        double outerX = pivotX + Math.cos(rads) * outerRadius;
        double outerY = pivotY + Math.sin(rads) * outerRadius;

        double centerX = pivotX + Math.cos(rads) * centerRadius;
        double centerY = pivotY + Math.sin(rads) * centerRadius;

        double endX = 0.0 + width / 2.0;
        double endY = 0.0;

        movementTemplate.getElements().add(new MoveTo(startX, startY));
        movementTemplate.getElements().add(new ArcTo(innerRadius, innerRadius, angleDegrees, innerX, innerY, false, true));
        movementTemplate.getElements().add(new LineTo(outerX, outerY));
        movementTemplate.getElements().add(new ArcTo(outerRadius, outerRadius, angleDegrees, endX, endY, false, false));
        movementTemplate.getElements().add(new LineTo(startX, startY));
        movementTemplate.setFill(Color.DARKGREEN);
        movementTemplate.setStroke(Color.WHITE);
        movementTemplate.setFillRule(FillRule.NON_ZERO);

        movementPath.getElements().add(new MoveTo(-SHIP_TEMPLATE_WIDTH/2.0, 0));
        movementPath.getElements().add(new LineTo(0, 0));
        movementPath.getElements().add(new ArcTo(centerRadius, centerRadius, angleDegrees, centerX, centerY, false, true));
        // TODO : fix this for the 45 degree template
        //movementPath.getElements().add(new LineTo(x+radius, y+radius + SHIP_TEMPLATE_WIDTH/2.0));
        movementPath.setStroke(Color.WHITE);
        movementPath.getStrokeDashArray().setAll(5d, 5d);

        return new MovementTemplate(movementPath, movementTemplate);
    }

    /**
     * Java main for when running without JavaFX launcher
     Perspective Camera
     Camera 3-5
     */
    public static void main(String[] args) {
        launch(args);
    }
}
