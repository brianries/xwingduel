package sample;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import movement.MovementDifficulty;
import movement.MovementTemplate;
import movement.MovementTemplateFactory;
import movement.MovementType;
import ship.*;


public class Main extends Application {

    private static final double BATTLE_GROUND_WIDTH_MM = 914.4;

    private BooleanProperty firingArcVisibile = new SimpleBooleanProperty(true);
    private double x1, y1;

    private Group root = new Group();
    private Group world = new Group();

    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    //private final Xform cameraXform = new Xform();

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
        shipToken.getTransforms().add(new Translate(0, 0, -0.01));      // IF NOT ADDED - ZCLIPPING DOES WEIRD THINGS TO TEXTURE

        final ShipOutlineToken outlineToken3 = new ShipOutlineToken(ShipSize.SMALL);
        outlineToken3.getTransforms().setAll(shipToken.getTransforms());

        movementTemplate3.getTransforms().setAll(shipToken.getTransforms());
        movementTemplate3.getTransforms().add(baseTranslate);

        shipToken.getTransforms().add(new Translate(ShipToken.SMALL_SHIP_TEMPLATE_WIDTH_MM / 2.0, 0));
        shipToken.getTransforms().add(movementTemplate3.getEndTransform());
        shipToken.getTransforms().add(new Translate(ShipToken.SMALL_SHIP_TEMPLATE_WIDTH_MM / 2.0, 0));

        firingArc.getTransforms().setAll(shipToken.getTransforms());
        firingArc.visibleProperty().bind(firingArcVisibile);
        firingArc.getTransforms().add(new Translate(0, 0, -0.05)); // make sure it appears over the rest of the items (and doesn't get Z-clipped)

        Rectangle playableArea = getPlayableArea();
        playableArea.getTransforms().add(new Translate(0, 0, 0.01));

        world.getChildren().add(playableArea);
        world.getChildren().add(shipToken);
        world.getChildren().add(outlineToken1);
        world.getChildren().add(outlineToken2);
        world.getChildren().add(outlineToken3);
        world.getChildren().add(movementTemplate1);
        world.getChildren().add(movementTemplate2);
        world.getChildren().add(movementTemplate3);
        world.getChildren().add(firingArc);

        world.setOnMousePressed(event -> {
            if (event.getTarget() instanceof ShipToken) {
                ((Rectangle) event.getTarget()).setStroke(Color.YELLOW);
            }
        });

        world.setOnMouseReleased(event -> {
            if (event.getTarget() instanceof ShipToken) {
                ((Rectangle) event.getTarget()).setStroke(Color.GRAY);
            }
        });

        world.addEventHandler(MouseEvent.ANY, e -> {
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
        return world;
    }

    public Rectangle getPlayableArea() {
        Rectangle rectangle = new Rectangle(BATTLE_GROUND_WIDTH_MM, BATTLE_GROUND_WIDTH_MM);
        Image texture = new Image("file:resources/starfield.jpg");
        ImagePattern imagePattern = new ImagePattern(texture);
        rectangle.setFill(imagePattern);
        return rectangle;
    }

    private void buildCamera() {
        root.getChildren().add(camera);
        //cameraXform.getChildren().add(camera);

        camera.setNearClip(100);
        camera.setFarClip(1000.0);
        camera.setTranslateX(BATTLE_GROUND_WIDTH_MM / 2.0);
        camera.setTranslateY(BATTLE_GROUND_WIDTH_MM / 2.0);
        camera.setTranslateZ(-(BATTLE_GROUND_WIDTH_MM / 2.0));
        camera.setFieldOfView(90.0);
        camera.setVerticalFieldOfView(true);

        //cameraXform.setTx(BATTLE_GROUND_WIDTH_MM / 2.0);
        //cameraXform.ry.setAngle(0.0);
        //cameraXform.rx.setAngle(0.0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(true);

        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);

        buildCamera();
        createContent(primaryStage);

        Scene scene = new Scene(root, 1200, 1200, true, SceneAntialiasing.BALANCED);
        Image texture = new Image("file:resources/wood-table.jpg");
        ImagePattern imagePattern = new ImagePattern(texture);
        scene.setFill(imagePattern);
        scene.setCamera(camera);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    // Do nothing
                    break;
                case F:
                    firingArcVisibile.set(!firingArcVisibile.get());
                    break;
            }
        });

        // Ensure we change the field of view for skinny wide or tall windows appropriately
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            double height = primaryStage.getHeight();
            setOptimalFieldOfView(width > height);
        });

        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            double width = primaryStage.getWidth();
            double height = newValue.doubleValue();
            setOptimalFieldOfView(width > height);
        });


        primaryStage.setTitle("XWing Duel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setOptimalFieldOfView(boolean widthGreaterThanHeight) {
        if (widthGreaterThanHeight) {
            camera.setVerticalFieldOfView(true);
        }
        else {
            camera.setVerticalFieldOfView(false);
        }
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
