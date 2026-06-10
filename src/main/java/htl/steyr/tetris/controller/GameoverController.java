package htl.steyr.tetris.controller;

import htl.steyr.tetris.utility.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class GameoverController {

    @FXML
    private Label scoreLabel;
    @FXML
    private Label highscoreLabel;
    @FXML
    private Button retryButton;
    @FXML
    private Button menuButton;

    private int score;
    private Preferences prefs = Preferences.userRoot().node("tetris");

    public void initialize() {
        retryButton.setOnAction(e -> restartGame());
        menuButton.setOnAction(e -> returnToMenu());
    }

    // Wird vom Spiel aufgerufen
    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText(String.valueOf(score));

        int highscore = prefs.getInt("highscore", 0);

        if (score > highscore) {
            prefs.putInt("highscore", score);
            highscore = score;
        }

        highscoreLabel.setText(String.valueOf(highscore));
    }

    private void restartGame() {
        ViewSwitcher.switchTo("game.fxml");
    }

    private void returnToMenu() {
        ViewSwitcher.switchTo("menu.fxml");
    }
}
