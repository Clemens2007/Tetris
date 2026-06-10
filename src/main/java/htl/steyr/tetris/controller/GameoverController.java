package htl.steyr.tetris.controller;

import htl.steyr.tetris.utility.ViewSwitcher;
import htl.steyr.tetris.user.UserData;
import htl.steyr.tetris.user.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

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

    public void initialize() {
        retryButton.setOnAction(e -> restartGame());
        menuButton.setOnAction(e -> returnToMenu());
    }

    //wird vom Spiel aufgerufen, um den Score zu setzen, wenn das Spiel vorbei ist
    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText(String.valueOf(score));

        UserData user = UserSession.getUserData();
        int oldHighscore = user.getHighscore();

        if (score > oldHighscore) {
            user.setHighscore(score);
            oldHighscore = score;
        }

        highscoreLabel.setText(String.valueOf(oldHighscore));
    }

    private void restartGame() {
        ViewSwitcher.switchTo("game.fxml");
    }

    private void returnToMenu() {
        ViewSwitcher.switchTo("menu.fxml");
    }
}
