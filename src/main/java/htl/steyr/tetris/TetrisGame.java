package htl.steyr.tetris;

import htl.steyr.tetris.key.KeyAssignment;
import htl.steyr.tetris.key.SaveKey;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class TetrisGame {
    private AnchorPane root;
    private Tile tile = new Tile();
    private Tile spare = new Tile();
    private Tile hold = new Tile();
    private int scale;
    private boolean holdBool = false;
    private String keyString;
    private KeyAssignment keys;
    private boolean waitingForKey = false;
    private String actionToBind = null;

    public TetrisGame(AnchorPane root){
        this.root = root;
        this.keys = SaveKey.load("default");

        root.getChildren().add(tile);
        scale = (int) tile.getScale();

        root.sceneProperty().addListener((obs, oldScene, scene) -> {
            if (scene != null) {
                root.setFocusTraversable(true);
                Platform.runLater(() -> root.requestFocus());
                scene.setOnKeyPressed(event -> {
                    if (waitingForKey) {
                        setKey(actionToBind, event.getCode());
                        waitingForKey = false;
                        actionToBind = null;
                        return;
                    }
                    move(event.getCode());
                });
            }
        });
    }

    public void update(double dt){
        // tile.moveHorizon(-1);
    }

    public void move(KeyCode key){
        if (keys == null) {
            return;
        }

        if (key == keys.getMoveLeft()) {
            tile.moveHorizon(scale);
        } else if (key == keys.getMoveRight()) {
            tile.moveHorizon(-scale);
        } else if (key == keys.getSoftDrop()) {
            tile.moveVertic(-scale);
        } else if (key == keys.getRotate()) {
            tile.rotato(90);
        } else if (key == keys.getHardDrop()) {
            tile.hardDrop();
        } else if (key == keys.getHold()) {
            handleHold();
        }
    }

    private void handleHold() {
        if (!holdBool) {
            hold = tile;
            holdBool = true;
            return;
        }

        spare = tile;
        tile = hold;
        hold = spare;
    }

    public void rebind(String action) {
        waitingForKey = true;
        actionToBind = action;
    }

    public void getKey(String key){
        this.keyString = key;
    }

    private void setKey(String action, KeyCode key) {
        switch (action) {
            case "left":
                keys.setMoveLeft(key);
                break;
            case "right":
                keys.setMoveRight(key);
                break;
            case "rotate":
                keys.setRotate(key);
                break;
            case "down":
                keys.setSoftDrop(key);
                break;
            case "hard":
                keys.setHardDrop(key);
                break;
            case "hold":
                keys.setHold(key);
                break;
        }

        SaveKey.save("default", keys);
    }
}
