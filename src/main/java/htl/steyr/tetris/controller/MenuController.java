package htl.steyr.tetris.controller;

import htl.steyr.tetris.highscore_management.HighscoreManager;
import htl.steyr.tetris.highscore_management.Score;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class MenuController {


    @FXML
    private Button closeButton;
    @FXML
    private Button playButton;
    @FXML
    private Button optionsButton;
    @FXML
    private Label welcomeLabel;
    @FXML
    private ListView<Score> highscoreList;
    @FXML
    private Pane adBanner;

    public void initialize(){
        highscoreList.getItems().addAll(HighscoreManager.loadHighscores());
    }

}