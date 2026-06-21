package htl.steyr.tetris.controller;

import htl.steyr.tetris.utility.ViewSwitcher;
import htl.steyr.tetris.user.UserData;
import htl.steyr.tetris.user.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import htl.steyr.tetris.highscore_management.HighscoreManager;
import htl.steyr.tetris.highscore_management.Score;

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

    private UserData ud;

    public void initialize() {
        retryButton.setOnAction(e -> restartGame());
        menuButton.setOnAction(e -> returnToMenu());
        ud = UserSession.getUserData();
        setScore(ud.getScore());
    }

    //wird vom Spiel aufgerufen, um den Score zu setzen, wenn das Spiel vorbei ist
    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText(String.valueOf(score));

        UserData ud = UserSession.getUserData();
        int oldHighscore = ud.getHighscore();

        if (score > oldHighscore) {
            ud.setHighscore(score);
            oldHighscore = score;
        }

        highscoreLabel.setText(String.valueOf(oldHighscore));

        HighscoreManager.writeHighscore(new Score(ud.getUsername(), score));
    }

    private void restartGame() {
        ud.save();
        ViewSwitcher.switchTo("game.fxml");
    }

    private void returnToMenu() {
        ud.save();
        ViewSwitcher.switchTo("menu.fxml");
    }
}
