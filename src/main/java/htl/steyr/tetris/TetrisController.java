package htl.steyr.tetris;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class TetrisController {

    @FXML
    private AnchorPane root;
    private TetrisGame tetrisGame;
    private GameLoop loop;
    private String keyString;

    public void initialize(){
        tetrisGame = new TetrisGame(root);
        loop = new GameLoop(tetrisGame);


        loop.start(); // Toast mit Schinken
    }

}
