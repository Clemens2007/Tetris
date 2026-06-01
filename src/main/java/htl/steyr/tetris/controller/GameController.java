package htl.steyr.tetris.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    @FXML
    private GridPane gameField;

    private static final int ROWS = 20;
    private static final int COLS = 10;
    private static final int CELL_SIZE = 24;

    private Rectangle[][] cells = new Rectangle[ROWS][COLS];
    private boolean[][] board = new boolean[ROWS][COLS];

    // Aktueller Block
    private int[][] shape;
    private int blockRow, blockCol;
    private Color blockColor;

    @FXML
    public void initialize() {

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Rectangle r = new Rectangle(CELL_SIZE, CELL_SIZE);
                r.setFill(Color.WHITE);
                r.setStroke(Color.LIGHTGRAY);
                r.setStrokeWidth(1);
                cells[row][col] = r;
                gameField.add(r, col, row);
            }
        }
    }


}