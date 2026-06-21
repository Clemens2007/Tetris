package htl.steyr.tetris;

import javafx.scene.paint.Color;

public class Board {

    public static final int ROWS = 20;
    public static final int COLS = 10;

    private final Color[][] grid = new Color[ROWS][COLS];

    public Color get(int row, int col) {
        return grid[row][col];
    }

    public boolean canPlace(int[][] shape, int newRow, int newCol) {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (shape[r][c] == 1) {
                    int boardRow = newRow + r;
                    int boardCol = newCol + c;

                    if (boardCol < 0 || boardCol >= COLS || boardRow >= ROWS) {
                        return false;
                    }
                    if (boardRow >= 0 && grid[boardRow][boardCol] != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void lock(int[][] shape, int row, int col, Color color) {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (shape[r][c] == 1) {
                    int boardRow = row + r;
                    int boardCol = col + c;
                    if (boardRow >= 0 && boardRow < ROWS && boardCol >= 0 && boardCol < COLS) {
                        grid[boardRow][boardCol] = color;
                    }
                }
            }
        }
    }

    public int clearFullLines() {
        int clearedLines = 0;

        for (int row = ROWS - 1; row >= 0; row--) {
            boolean full = true;
            for (int col = 0; col < COLS; col++) {
                if (grid[row][col] == null) {
                    full = false;
                    break;
                }
            }

            if (full) {
                clearedLines++;
                for (int r = row; r > 0; r--) {
                    grid[r] = grid[r - 1].clone();
                }
                grid[0] = new Color[COLS];
                row++; // gleiche Reihe nochmal prüfen
            }
        }
        return clearedLines;
    }
}