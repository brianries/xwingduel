package sample;

import gui.MainPanel;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
import rendering.BoardScene;
import rendering.movement.MovementTemplate;
import rendering.movement.MovementTemplateFactory;
import movement.MovementType;
import rendering.firingarc.FiringArc;
import rendering.obstacles.ObstacleToken;
import rendering.obstacles.ObstacleType;
import rendering.playarea.PlayArea;
import rendering.ship.ShipOutlineToken;
import rendering.ship.ShipToken;
import ship.*;
import rendering.ship.ShipTokenPart;
import state.LocalBoardState;


public class Main extends Application {

    private BooleanProperty firingArcVisibile = new SimpleBooleanProperty(true);
    private double x1, y1;

    @Deprecated
    public void populateBoardScene(BoardScene boardScene) throws Exception {
        final MovementTemplateFactory factory = new MovementTemplateFactory();
        final ShipToken shipToken = new ShipToken("resources/ships/tie-fighter.png", ShipSize.SMALL);
        final FiringArc firingArc = new FiringArc(FiringArcRange.THREE, ShipSize.SMALL);
        final ObstacleToken asteroid = new ObstacleToken(ObstacleType.ASTEROID_BASE_CORE_0);

        final MovementTemplate movementTemplate1 = factory.constructMovementTemplate(MovementDifficulty.GREEN, MovementType.LEFT_BANK_1);
        final MovementTemplate movementTemplate2 = factory.constructMovementTemplate(MovementDifficulty.RED, MovementType.RIGHT_HARD_3);
        final MovementTemplate movementTemplate3 = factory.constructMovementTemplate(MovementDifficulty.WHITE, MovementType.FORWARD_5);

        // Initial placement
        Translate translate = new Translate(200,200);
        Rotate rotate = new Rotate(10);
        shipToken.getTransforms().add(translate);
        shipToken.getTransforms().add(rotate);

        asteroid.getTransforms().add(new Translate(300,300));

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

        boardScene.getChildren().add(playableArea);
        boardScene.getChildren().add(asteroid);
        boardScene.getChildren().add(shipToken);
        boardScene.getChildren().add(outlineToken1);
        boardScene.getChildren().add(outlineToken2);
        boardScene.getChildren().add(outlineToken3);
        boardScene.getChildren().add(movementTemplate1);
        boardScene.getChildren().add(movementTemplate2);
        boardScene.getChildren().add(movementTemplate3);
        boardScene.getChildren().add(firingArc);
        boardScene.getChildren().add(shipLabel);

        boardScene.addEventHandler(MouseEvent.ANY, event -> {
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
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(true);

        LocalBoardState localBoardState = new LocalBoardState();
        MainPanel mainPanel = new MainPanel(localBoardState);

        populateBoardScene(mainPanel.getBoardScene());

        Scene topScene = new Scene(mainPanel, 1200, 1200, false, SceneAntialiasing.BALANCED);
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

        primaryStage.setTitle("XWing Duel");
        primaryStage.setScene(topScene);
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
