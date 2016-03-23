package sample;

import gui.MainPanel;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import movement.MovementDifficulty;
import rendering.BoardScene;
import rendering.movement.MovementTemplate;
import rendering.movement.MovementTemplateFactory;
import movement.MovementType;
import rendering.firingarc.FiringArc;
import rendering.obstacles.ObstacleToken;
import rendering.obstacles.ObstacleTokenPart;
import rendering.obstacles.ObstacleType;
import rendering.playarea.PlayArea;
import rendering.ship.ShipOutlineToken;
import rendering.ship.ShipToken;
import ship.*;
import state.LocalBoardState;


public class Main extends Application {

    private BooleanProperty firingArcVisibile = new SimpleBooleanProperty(true);
    private double x1, y1;

    @Deprecated
    public void populateBoardScene(BoardScene boardScene) throws Exception {
        final MovementTemplateFactory factory = new MovementTemplateFactory();
        final ShipToken shipToken = new ShipToken("resources/ships/tie-fighter.png", ShipSize.SMALL);
        final FiringArc firingArc = new FiringArc(FiringArcRange.THREE, ShipSize.SMALL);
        final ObstacleToken asteroid0 = new ObstacleToken(ObstacleType.ASTEROID_BASE_CORE_0);
        final ObstacleToken asteroid1 = new ObstacleToken(ObstacleType.ASTEROID_BASE_CORE_1);
        final ObstacleToken asteroid2 = new ObstacleToken(ObstacleType.ASTEROID_BASE_CORE_2);
        final ObstacleToken asteroid3 = new ObstacleToken(ObstacleType.ASTEROID_BASE_CORE_3);
        final ObstacleToken asteroid4 = new ObstacleToken(ObstacleType.ASTEROID_BASE_CORE_4);
        final ObstacleToken asteroid5 = new ObstacleToken(ObstacleType.ASTEROID_BASE_CORE_5);


        final MovementTemplate movementTemplate1 = factory.constructMovementTemplate(MovementDifficulty.GREEN, MovementType.LEFT_BANK_1);
        final MovementTemplate movementTemplate2 = factory.constructMovementTemplate(MovementDifficulty.RED, MovementType.RIGHT_HARD_3);
        final MovementTemplate movementTemplate3 = factory.constructMovementTemplate(MovementDifficulty.WHITE, MovementType.FORWARD_5);

        // Initial placement
        Translate translate = new Translate(200,200);
        Rotate rotate = new Rotate(10);
        shipToken.getTransforms().add(translate);
        shipToken.getTransforms().add(rotate);

        asteroid0.getTransforms().add(new Translate(450,100));
        asteroid1.getTransforms().add(new Translate(300,300));
        asteroid2.getTransforms().add(new Translate(400,450));
        asteroid3.getTransforms().add(new Translate(700,200));
        asteroid4.getTransforms().add(new Translate(150,700));
        asteroid5.getTransforms().add(new Translate(600,600));


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
        shipLabel.setText("Tie Academy Pilot 1");
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
        boardScene.getChildren().add(asteroid0);
        boardScene.getChildren().add(asteroid1);
        boardScene.getChildren().add(asteroid2);
        boardScene.getChildren().add(asteroid3);
        boardScene.getChildren().add(asteroid4);
        boardScene.getChildren().add(asteroid5);
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
            if (ObstacleTokenPart.class.isAssignableFrom(event.getTarget().getClass())) {
                ObstacleToken obstacleToken = ((ObstacleTokenPart)event.getTarget()).getObstacleToken();
                if (event.getEventType() == MouseEvent.MOUSE_ENTERED_TARGET) {
                    obstacleToken.setHighlighted(true);
                }
                else if (event.getEventType() == MouseEvent.MOUSE_EXITED_TARGET) {
                    obstacleToken.setHighlighted(false);
                }
                else if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    x1 = event.getX();
                    y1 = event.getY();
                    obstacleToken.setSelected(true);
                }
                else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    // translate node
                    if (!obstacleToken.translateXProperty().isBound()) {
                        obstacleToken.setTranslateX(event.getX() - x1 + obstacleToken.getTranslateX());
                    }
                    if (!obstacleToken.translateYProperty().isBound()) {
                        obstacleToken.setTranslateY(event.getY() - y1 + obstacleToken.getTranslateY());
                    }
                    x1 = event.getX();
                    y1 = event.getY();
                }
                else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                    obstacleToken.setSelected(false);
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    // right-click over the path to move it to its original position
                    if (!obstacleToken.translateXProperty().isBound()) {
                        obstacleToken.setTranslateX(0);
                        obstacleToken.setTranslateY(0);
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

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Sample Popup Window");

        Pane pane = FXMLLoader.load(getClass().getResource("../layout/initiative.fxml"));
        Scene dialogScene = new Scene(pane, 500, 500);
        dialog.setScene(dialogScene);
        dialog.show();
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
