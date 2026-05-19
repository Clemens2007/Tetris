

package htl.steyr.tetris;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;



public class TetrisGame {

    private boolean x = false;
    private AnchorPane root;
    private Tile tile = new Tile();
    private Tile spare = new Tile();
    private String keyString;


    public TetrisGame(AnchorPane root){
        this.root = root;
        root.getChildren().add(tile);

        root.sceneProperty().addListener((obs, oldScene, scene) -> {
            if (scene != null) {
                root.setFocusTraversable(true);
                Platform.runLater(() -> root.requestFocus());
                root.setOnKeyPressed(event -> {
                    keyString = event.getCode().toString();
                    if(keyString == "I"){

                    }
                    //System.out.println(keyString);
                    move(keyString);
                });
            }
        });
    }

    public void update(double dt){

    }

    public void move(String keybind){
        switch (keybind){
            case "LEFT": {
                tile.moveVertic(40);
                break;
            }
            case "RIGHT": {
                tile.moveVertic(- 40);
                break;
            }
            case "UP": {
                tile.rotato(90);
                break;
            }
            case "X": {
                tile.rotato(- 90);
                break;
            }
            case "DOWN": {
                tile.moveHorizon(- 40);
                break;
            }

        }
    }

    public void getKey(String key){
        this.keyString = key;
    }


}
