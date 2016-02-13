package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import rendering.BoardScene;
import state.LocalBoardState;

/**
 * Created by Brian on 2/13/2016.
 */
public class MainPanel extends BorderPane {

    private GameBoardPane gameBoardPane;
    private LocalBoardState localBoardState;

    public MainPanel(LocalBoardState localBoardState) {
        super();
        this.localBoardState = localBoardState;
        init();
    }

    private void init() {
        StackPane pane1 = new StackPane();
        pane1.setStyle("-fx-background-color: red");
        Button testButton1 = new Button("Top Panel");
        pane1.getChildren().add(testButton1);
        pane1.setAlignment(testButton1, Pos.CENTER);
        this.setTop(pane1);

        StackPane pane2 = new StackPane();
        pane2.setStyle("-fx-background-color: blue");
        Button testButton2 = new Button("Bottom Panel");
        pane2.getChildren().add(testButton2);
        pane2.setAlignment(testButton2, Pos.CENTER);
        this.setBottom(pane2);

        StackPane pane3 = new StackPane();
        pane3.setStyle("-fx-background-color: green");
        Button testButton3 = new Button("Right Panel");
        pane3.getChildren().add(testButton3);
        pane3.setAlignment(testButton3, Pos.CENTER);
        this.setRight(pane3);

        StackPane pane4 = new StackPane();
        pane4.setStyle("-fx-background-color: yellow");
        Button testButton4 = new Button("Left Panel");
        pane4.getChildren().add(testButton4);
        pane4.setAlignment(testButton4, Pos.CENTER);
        this.setLeft(pane4);

        gameBoardPane = new GameBoardPane(localBoardState);
        this.setCenter(gameBoardPane);
    }

    @Deprecated
    public BoardScene getBoardScene() {
        return gameBoardPane.getBoardScene();
    }
}
