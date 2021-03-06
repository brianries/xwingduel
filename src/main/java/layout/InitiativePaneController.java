package layout;

import dws.Roller;
import dws.XRedDie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class InitiativePaneController implements Initializable {

    private String message = null;

    @FXML
    private Button rollInitButton;

    @FXML
    private TextArea player1TextArea;

    @FXML
    private TextArea player2TextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert rollInitButton  != null : "fx:id=\"rollInitButton\" was not injected: check your FXML file 'InitiativePane.fxml'.";
        assert player1TextArea != null : "fx:id=\"player1TextArea\" was not injected: check your FXML file 'InitiativePane.fxml'.";
        assert player2TextArea != null : "fx:id=\"player2TextArea\" was not injected: check your FXML file 'InitiativePane.fxml'.";

//        rollInitButton.setOnAction(event -> System.out.println("Message = " + message));
    }

    public void initData(String message) {
        this.message = message;
    }

    @FXML protected void handleRollInitiativeButtonAction(ActionEvent event) {
        System.out.println("button pressed event");
        String resultPlayer1 = Roller.roll(Roller.xRedDie, 4);
        System.out.println(resultPlayer1);
        String resultPlayer2 = Roller.roll(Roller.xRedDie, 4);
        System.out.println(resultPlayer2);
        player1TextArea.setText(resultPlayer1);
        player2TextArea.setText(resultPlayer2);
    }

}