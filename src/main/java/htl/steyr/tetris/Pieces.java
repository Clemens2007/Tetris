package htl.steyr.tetris;

import javafx.scene.paint.Color;

import java.util.Random;

public class Pieces {

    private static final int[][][] SHAPES = {
            {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}}, // I
            {{0,0,0,0},{0,1,1,0},{0,1,1,0},{0,0,0,0}}, // O
            {{0,1,0,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}}, // T
            {{0,1,1,0},{1,1,0,0},{0,0,0,0},{0,0,0,0}}, // S
            {{1,1,0,0},{0,1,1,0},{0,0,0,0},{0,0,0,0}}, // Z
            {{1,0,0,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}}, // J
            {{0,0,1,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}}  // L
    };

    private static final Color[] COLORS = {
            Color.CYAN, Color.YELLOW, Color.PURPLE, Color.GREEN,
            Color.RED, Color.BLUE, Color.ORANGE
    };

    private static final Random random = new Random();

    private int[][] shape;
    private final Color color;

    public Pieces(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public static Pieces random() {
        int index = random.nextInt(SHAPES.length);
        return new Pieces(SHAPES[index], COLORS[index]);
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int[][] rotated(boolean clockwise) {
        int[][] result = new int[4][4];

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (clockwise) {
                    result[c][3 - r] = shape[r][c];
                } else {
                    result[3 - c][r] = shape[r][c];
                }
            }
        }
        return result;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }
}