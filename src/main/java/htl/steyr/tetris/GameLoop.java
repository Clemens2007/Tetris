package htl.steyr.tetris;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {
    private final TetrisGame tetrisGame;
    private long lastTime = 0;

    public GameLoop(TetrisGame tetrisGame) {
        this.tetrisGame = tetrisGame;
    }

    @Override
    public void handle(long now) {
        if (lastTime == 0) {
            lastTime = now;
            return;
        }

        double dt =
                (now - lastTime) / 1_000_000_000.0;

        lastTime = now;

        tetrisGame.update(dt);
    }
}