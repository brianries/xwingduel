package sample;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import movement.MovementDifficulty;
import rendering.movement.MovementTemplate;
import rendering.movement.MovementTemplateFactory;
import movement.MovementType;
import rendering.firingarc.FiringArc;
import rendering.playarea.PlayArea;
import rendering.ship.ShipOutlineToken;
import rendering.ship.ShipToken;
import ship.*;
import rendering.ship.ShipTokenPart;


public class Main extends Application {

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

        Rectangle playableArea = new PlayArea();
        playableArea.getTransforms().add(new Translate(0, 0, 0.01));

        Text shipLabel = new Text();
        shipLabel.setText("Gold Sq Pilot 1");
        shipLabel.setFont(Font.font("Verdana", 12));
        shipLabel.setFill(Color.ANTIQUEWHITE);
        double width = shipLabel.getLayoutBounds().getWidth();

        // Construct the final result of the ship transformations, so the translates can be extracted
        Affine result = new Affine();
        for (Transform transform : shipToken.getTransforms()) {
            result.append(transform);
        }
        shipLabel.setTranslateX(result.getTx());
        shipLabel.setTranslateY(result.getTy());
        shipLabel.getTransforms().add(new Translate(-width/2.0, 30.0, -1.0));

        world.getChildren().add(playableArea);
        world.getChildren().add(shipToken);
        world.getChildren().add(outlineToken1);
        world.getChildren().add(outlineToken2);
        world.getChildren().add(outlineToken3);
        world.getChildren().add(movementTemplate1);
        world.getChildren().add(movementTemplate2);
        world.getChildren().add(movementTemplate3);
        world.getChildren().add(firingArc);
        world.getChildren().add(shipLabel);

        world.addEventHandler(MouseEvent.ANY, event -> {
            if (ShipTokenPart.class.isAssignableFrom(event.getTarget().getClass())) {
                ShipToken shipToken1 = ((ShipTokenPart)event.getTarget()).getShipToken();
                if (event.getEventType() == MouseEvent.MOUSE_ENTERED_TARGET) {
                    shipToken1.setHighlighted(true);
                }
                else if (event.getEventType() == MouseEvent.MOUSE_EXITED_TARGET) {
                    shipToken1.setHighlighted(false);
                }
                else if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    x1 = event.getX();
                    y1 = event.getY();
                    shipToken1.setSelected(true);
                }
                else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    // translate node
                    if (!shipToken1.translateXProperty().isBound()) {
                        shipToken1.setTranslateX(event.getX() - x1 + shipToken1.getTranslateX());
                    }
                    if (!shipToken1.translateYProperty().isBound()) {
                        shipToken1.setTranslateY(event.getY() - y1 + shipToken1.getTranslateY());
                    }
                    x1 = event.getX();
                    y1 = event.getY();
                }
                else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                    shipToken1.setSelected(false);
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    // right-click over the path to move it to its original position
                    if (!shipToken1.translateXProperty().isBound()) {
                        shipToken1.setTranslateX(0);
                        shipToken1.setTranslateY(0);
                    }
                }

            }
        });
        return world;
    }



    private void buildCamera() {
        //root.getChildren().add(camera);
        //cameraXform.getChildren().add(camera);

        camera.setNearClip(100);
        camera.setFarClip(1000.0);
        camera.setTranslateX(PlayArea.BATTLE_GROUND_WIDTH_MM / 2.0);
        camera.setTranslateY(PlayArea.BATTLE_GROUND_WIDTH_MM / 2.0);
        camera.setTranslateZ(-(PlayArea.BATTLE_GROUND_WIDTH_MM / 2.0));
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

        //StackPane rootPane = new StackPane();
        BorderPane borderPane = new BorderPane();

        StackPane pane1 = new StackPane();
        pane1.setStyle("-fx-background-color: red");
        Button testButton1 = new Button("Top Panel");
        pane1.getChildren().add(testButton1);
        pane1.setAlignment(testButton1, Pos.CENTER);
        borderPane.setTop(pane1);

        StackPane pane2 = new StackPane();
        pane2.setStyle("-fx-background-color: blue");
        Button testButton2 = new Button("Bottom Panel");
        pane2.getChildren().add(testButton2);
        pane2.setAlignment(testButton2, Pos.CENTER);
        borderPane.setBottom(pane2);

        StackPane pane3 = new StackPane();
        pane3.setStyle("-fx-background-color: green");
        Button testButton3 = new Button("Right Panel");
        pane3.getChildren().add(testButton3);
        pane3.setAlignment(testButton3, Pos.CENTER);
        borderPane.setRight(pane3);

        StackPane pane4 = new StackPane();
        pane4.setStyle("-fx-background-color: yellow");
        Button testButton4 = new Button("Left Panel");
        pane4.getChildren().add(testButton4);
        pane4.setAlignment(testButton4, Pos.CENTER);
        borderPane.setLeft(pane4);

        SubScene subScene = new SubScene(root, 600, 600, true, SceneAntialiasing.BALANCED);
        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(subScene);
        centerPane.setMinSize(0, 0);
        centerPane.setAlignment(subScene, Pos.CENTER);
        subScene.heightProperty().bind(centerPane.heightProperty());
        subScene.widthProperty().bind(centerPane.widthProperty());

        //Image texture = new Image("file:resources/wood-table.jpg");
        //ImagePattern imagePattern = new ImagePattern(texture);
        subScene.setFill(Color.rgb(30, 30, 30));
        subScene.setCamera(camera);
        borderPane.setCenter(centerPane);

        Scene topScene = new Scene(borderPane, 1200, 1200, false, SceneAntialiasing.BALANCED);

        topScene.setOnKeyPressed(event -> {
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
        centerPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            double height = centerPane.getHeight();
            setOptimalFieldOfView(width > height);
        });

        centerPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double width = centerPane.getWidth();
            double height = newValue.doubleValue();
            setOptimalFieldOfView(width > height);
        });


        primaryStage.setTitle("XWing Duel");
        primaryStage.setScene(topScene);
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
