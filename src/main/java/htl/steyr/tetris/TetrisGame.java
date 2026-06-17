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
    private int blocksize;
    private boolean holdBool = false;
    private String keyString;
    private KeyAssignment keys;
    private boolean waitingForKey = false;
    private String actionToBind = null;
    private static final int COLS = 10;
    private static final int ROWS = 20;
    private boolean[][] grid = new boolean[ROWS][COLS];
    private double fallTimer = 0;
    private double fallInterval = 0.5;

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
        tile.randomTile("");
        blocksize = (int) tile.getScale();

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
        fallTimer += dt;

        if (fallTimer >= fallInterval) {
            fallTimer = 0;

            if (canMoveDown(tile)) {
                tile.moveVertic(-blocksize);
            } else {
                freezeTile(tile);
                spawnNewTile();
            }
        }
    }

    public void move(KeyCode key){
        if (keys == null) {
            return;
        }

        if (key == keys.getMoveLeft()) {
            tile.moveHorizon(blocksize);
        } else if (key == keys.getMoveRight()) {
            tile.moveHorizon(-blocksize);
        } else if (key == keys.getSoftDrop()) {
            tile.moveVertic(-blocksize);
        } else if (key == keys.getRotateRight()) {
            tile.rotato(90);
        } else if (key == keys.getRotateLeft()) {
            tile.rotato(-90);
        } else if (key == keys.getHardDrop()) {
            hardDrop();
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

    private boolean canMoveDown(Tile t) {
        int[][] cells = t.getTakenCells();

        for (int[] cell : cells) {
            int col = cell[0];
            int row = cell[1] + 1;

            if (row >= ROWS) {
                return false;
            }

            if (grid[row][col]) {
                return false;
            }
        }
        return true;
    }

    private void freezeTile(Tile t) {
        int[][] cells = t.getTakenCells();

        for (int[] cell : cells) {
            int col = cell[0];
            int row = cell[1];

            if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
                grid[row][col] = true;
            }
        }
    }

    private void spawnNewTile() {
        root.getChildren().remove(tile);
        tile = new Tile();
        root.getChildren().add(tile);
        tile.randomTile("");
        tile.applyCss();
        tile.layout();
    }

    private void hardDrop() {
        int safety = 0;
        while (canMoveDown(tile) && safety < ROWS) {
            tile.moveVertic(-blocksize);
            safety++;
        }
        freezeTile(tile);
        spawnNewTile();
    }
}
