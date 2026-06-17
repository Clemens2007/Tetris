

package htl.steyr.tetris;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TetrisGame {

    private Pane gameLayer;
    private Tile tile = new Tile();
    private Tile spare = new Tile();
    private Tile hold = new Tile();
    private Tile fieldOfTiles = new Tile();
    private boolean holdBool = false;
    private String keyString;

    private int x;
    private int y;

    private int row;
    private int col;

    private boolean[][] board = new boolean[row][col];


    private double unit = 40;
    private double scale = 0.75;
    private double blockSize = unit * scale;

    public TetrisGame(Pane gamePane, int row, int col) {
        this.row = row;
        this.col = col;


        gameLayer = gamePane;
        gameLayer.getChildren().add(tile);
        gameLayer.getChildren().add(fieldOfTiles);
        scale = (int) tile.getScale();
    }

    public void update(double dt){
        // tile.moveHorizon(-1);
    }

    public void move(String keybind){
        switch (keybind){
            case "LEFT": {
                if (checkBounds(-blockSize, 0)){
                    tile.setLayoutX(tile.getLayoutX() - blockSize); break;
                }
                break;
            }
            case "RIGHT": {
                if (checkBounds(blockSize, 0)){
                    tile.setLayoutX(tile.getLayoutX() + blockSize); break;
                }
                break;
            }
            case "DOWN": {
                moveDown();
                break;
            }
            case "UP": {tile.rotato(90); break;}
            case "X": {tile.rotato(- 90); break;}
            case "Y": {
                if(!holdBool){
                    hold = tile;
                    // new random tile spawn here
                    holdBool = true;
                    break;
                }
                spare = tile;
                tile = hold;
                hold = spare;
                break;
            }
            case "SPACE": {
                // the moveDown function (collision check needed)
                break;
            }

        }
    }

    private boolean checkBounds(double dx, double dy) {
        Bounds bounds = tile.getBoundsInParent();

        double newMinX = bounds.getMinX() + dx;
        double newMaxX = bounds.getMaxX() + dx;

        double newMinY = bounds.getMinY() + dy;
        double newMaxY = bounds.getMaxY() + dy;

        return newMinX >= 0 &&
                newMaxX <= gameLayer.getWidth() &&
                newMinY >= 0 &&
                newMaxY <= gameLayer.getHeight();
    }

    public void getKey(String key){
        this.keyString = key;
    }

    // ---------------- des fian loop
    public void moveDown(){
        if (checkBounds(0,  blockSize)){
            tile.setLayoutY(tile.getLayoutY() + blockSize);
        } else {
            fieldOfTiles.getChildren().add(tile);
            tile = new Tile();
            gameLayer.getChildren().add(tile);
            setStartPosition(x, y);
        }
    }

    private boolean collidesWithPlacedTiles(double dx, double dy) {

        Bounds b = tile.getBoundsInParent();

        double newMinX = b.getMinX() + dx;
        double newMaxX = b.getMaxX() + dx;

        double newMinY = b.getMinY() + dy;
        double newMaxY = b.getMaxY() + dy;

        int left   = (int)(newMinX / blockSize);
        int right  = (int)(newMaxX / blockSize);
        int top    = (int)(newMinY / blockSize);
        int bottom = (int)(newMaxY / blockSize);

        // check all grid cells covered by bounding box
        for (int y = top; y <= bottom; y++) {
            for (int x = left; x <= right; x++) {

                if (x < 0 || x >= col || y < 0 || y >= row) {
                    return true;
                }

                if (board[y][x]) {
                    return true;
                }
            }
        }

        return false;
    }

    public void setStartPosition(int x, int y){
        this.x = x;
        this.y = y;
        tile.setLayoutX(x/2);
        tile.setLayoutY(blockSize*2);


    }



}
