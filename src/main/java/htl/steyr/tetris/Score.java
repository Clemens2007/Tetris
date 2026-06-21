package htl.steyr.tetris;

public class Score {

    private static final int SINGLE = 100;
    private static final int DOUBLE = 300;
    private static final int TRIPLE = 500;
    private static final int TETRIS = 800;

    private static final int TSPIN = 400;
    private static final int TSPIN_DOUBLE = 1200;
    private static final int TSPIN_TRIPLE = 1600;

    private static final int SOFT_DROP_PER_ROW = 1;
    private static final int HARD_DROP_PER_ROW = 2;

    public int linesCleared(int lines) {
        switch (lines) {
            case 1: return SINGLE;
            case 2: return DOUBLE;
            case 3: return TRIPLE;
            case 4: return TETRIS;
            default: return 0;
        }
    }

    public int tSpin(int lines) {
        switch (lines) {
            case 0: return TSPIN;
            case 1: return TSPIN;
            case 2: return TSPIN_DOUBLE;
            case 3: return TSPIN_TRIPLE;
            default: return 0;
        }
    }

    public int softDrop(int rowsFallen) {
        return rowsFallen * SOFT_DROP_PER_ROW;
    }

    public int hardDrop(int rowsFallen) {
        return rowsFallen * HARD_DROP_PER_ROW;
    }
}