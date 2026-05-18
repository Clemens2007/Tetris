package htl.steyr.tetris;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TetrisController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
