package htl.steyr.tetris;

import htl.steyr.tetris.key.KeyAssignment;
import htl.steyr.tetris.user.UserSession;
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
        this.keys = new KeyAssignment();

        keys.setMoveLeft(UserSession.getUserData().getSetting("left"));
        keys.setMoveRight(UserSession.getUserData().getSetting("right"));
        keys.setSoftDrop(UserSession.getUserData().getSetting("softdrop"));
        keys.setHardDrop(UserSession.getUserData().getSetting("harddrop"));
        keys.setHold(UserSession.getUserData().getSetting("hold"));
        keys.setRotateRight(UserSession.getUserData().getSetting("rotate_right"));
        keys.setRotateLeft(UserSession.getUserData().getSetting("rotate_left"));
        keys.setMoveDown(UserSession.getUserData().getSetting("down"));

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
        } else if (key == keys.getRotateRight()) {
            tile.rotato(90);
        } else if (key == keys.getRotateLeft()) {
            tile.rotato(-90);
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
                UserSession.getUserData().setSetting("left", key);
                break;
            case "right":
                keys.setMoveRight(key);
                UserSession.getUserData().setSetting("right", key);
                break;
            case "rotate_right":
                keys.setRotateRight(key);
                UserSession.getUserData().setSetting("rotate_right", key);
                break;
            case "rotate_left":
                keys.setRotateLeft(key);
                UserSession.getUserData().setSetting("rotate_left", key);
                break;
            case "down":
                keys.setSoftDrop(key);
                UserSession.getUserData().setSetting("softdrop", key);
                break;
            case "hard":
                keys.setHardDrop(key);
                UserSession.getUserData().setSetting("harddrop", key);
                break;
            case "hold":
                keys.setHold(key);
                UserSession.getUserData().setSetting("hold", key);
                break;
        }

        UserSession.getUserData().save();
    }
}
