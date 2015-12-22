package sample;

import com.sun.javafx.fxml.builder.TriangleMeshBuilder;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import movement.*;
import ship.*;


public class Main extends Application {

    private BooleanProperty firingArcVisibile = new SimpleBooleanProperty(true);
    private TemplateTransition templateTransition;
    private double x1, y1;

    public Parent createContent(Stage stage) throws Exception {

        final MovementTemplateFactory factory = new MovementTemplateFactory();
        final ShipToken shipToken = new ShipToken("resources/ywing_rotated.jpg", ShipSize.SMALL);
        final FiringArc firingArc = new FiringArc(FiringArcRange.THREE, ShipSize.SMALL);

        final MovementTemplate movementTemplate1 = factory.constructMovementTemplate(MovementDifficulty.GREEN, MovementType.LEFT_BANK_1);
        final MovementTemplate movementTemplate2 = factory.constructMovementTemplate(MovementDifficulty.RED, MovementType.RIGHT_HARD_3);
        final MovementTemplate movementTemplate3 = factory.constructMovementTemplate(MovementDifficulty.WHITE, MovementType.FORWARD_5);

        // Initial placement
        Translate translate = new Translate(200,200);
        Rotate rotate = new Rotate(10);
        shipToken.getTransforms().add(translate);
        shipToken.getTransforms().add(rotate);


        Translate baseTranslate = new Translate(ShipToken.SMALL_SHIP_TEMPLATE_WIDTH_MM / 2.0, 0.0);

        final ShipOutlineToken outlineToken1 = new ShipOutlineToken(ShipSize.SMALL);
        outlineToken1.getTransforms().setAll(shipToken.getTransforms());

        movementTemplate1.getTransforms().setAll(shipToken.getTransforms());
        movementTemplate1.getTransforms().add(baseTranslate);

        shipToken.getTransforms().add(new Translate(ShipToken.SMALL_SHIP_TEMPLATE_WIDTH_MM / 2.0, 0));
        shipToken.getTransforms().add(movementTemplate1.getEndTransform());
        shipToken.getTransforms().add(new Translate(ShipToken.SMALL_SHIP_TEMPLATE_WIDTH_MM / 2.0, 0));

        final ShipOutlineToken outlineToken2 = new ShipOutlineToken(ShipSize.SMALL);
        outlineToken2.getTransforms().setAll(shipToken.getTransforms());

        movementTemplate2.getTransforms().setAll(shipToken.getTransforms());
        movementTemplate2.getTransforms().add(baseTranslate);

        shipToken.getTransforms().add(new Translate(ShipToken.SMALL_SHIP_TEMPLATE_WIDTH_MM / 2.0, 0));
        shipToken.getTransforms().add(movementTemplate2.getEndTransform());
        shipToken.getTransforms().add(new Translate(ShipToken.SMALL_SHIP_TEMPLATE_WIDTH_MM / 2.0, 0));


        final ShipOutlineToken outlineToken3 = new ShipOutlineToken(ShipSize.SMALL);
        outlineToken3.getTransforms().setAll(shipToken.getTransforms());

        movementTemplate3.getTransforms().setAll(shipToken.getTransforms());
        movementTemplate3.getTransforms().add(baseTranslate);

        shipToken.getTransforms().add(new Translate(ShipToken.SMALL_SHIP_TEMPLATE_WIDTH_MM / 2.0, 0));
        shipToken.getTransforms().add(movementTemplate3.getEndTransform());
        shipToken.getTransforms().add(new Translate(ShipToken.SMALL_SHIP_TEMPLATE_WIDTH_MM / 2.0, 0));

        firingArc.getTransforms().setAll(shipToken.getTransforms());
        firingArc.visibleProperty().bind(firingArcVisibile);

        // Build the Scene Graph
        Group root = new Group();

        Group background = new Group();
        background.getChildren().add(getPlayableArea());
        SubScene subScene = new SubScene(background, 1000, 1000, true, SceneAntialiasing.DISABLED);
        subScene.widthProperty().bind(stage.widthProperty());
        subScene.heightProperty().bind(stage.heightProperty());
        root.getChildren().add(subScene);

        root.getChildren().add(shipToken);
        root.getChildren().add(outlineToken1);
        root.getChildren().add(outlineToken2);
        root.getChildren().add(outlineToken3);
        root.getChildren().add(movementTemplate1);
        root.getChildren().add(movementTemplate2);
        root.getChildren().add(movementTemplate3);
        root.getChildren().add(firingArc);


        root.setOnMousePressed(event -> {
            if (event.getTarget() instanceof ShipToken) {
                ((Rectangle) event.getTarget()).setStroke(Color.YELLOW);
            }
        });

        root.setOnMouseReleased(event -> {
            if (event.getTarget() instanceof ShipToken) {
                ((Rectangle) event.getTarget()).setStroke(Color.GRAY);
            }
        });

        root.addEventHandler(MouseEvent.ANY, e -> {
            if(e.getTarget() instanceof ShipToken ) {
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

        root.setTranslateX(100);
        root.setTranslateY(100);
        return root;
    }

    public Rectangle getPlayableArea() {
        Rectangle rectangle = new Rectangle(914.4, 914.4);
        rectangle.setTranslateZ(1);
        Image texture = new Image("file:resources/starfield.jpg");
        ImagePattern imagePattern = new ImagePattern(texture);
        rectangle.setFill(imagePattern);
        return rectangle;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(true);
        Scene scene = new Scene(createContent(primaryStage), 1114.4, 1114.4, true, SceneAntialiasing.BALANCED);

        Image texture = new Image("file:resources/wood-table.jpg");
        ImagePattern imagePattern = new ImagePattern(texture);
        scene.setFill(imagePattern);

        ParallelCamera parallelCamera = new ParallelCamera();
        scene.setCamera(parallelCamera);

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

    /**
     * Java main for when running without JavaFX launcher
     Perspective Camera
     Camera 3-5
     */
    public static void main(String[] args) {
        launch(args);
    }
}
