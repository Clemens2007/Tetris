package htl.steyr.tetris;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {

    private final TetrisGame tetrisGame;
    private long lastTime = 0;
    private boolean running = true;
    private static final double step = 1.0 / 120.0; // simulation ticks
    private double speed = 1.0; // 1x speed

    // fps display (weil es kuman sekunden pro frame)
    private int frames = 0;
    private long lastFpsTime = 0;
    private int fps = 0;
    private boolean framerateSwitch = false;

    public GameLoop(TetrisGame tetrisGame) {
        this.tetrisGame = tetrisGame;
    }

    public void hanlde(long now) {

    }

    public void pause() {
        running = false;
    }

    public void resume() {
        lastTime = 0;
        running = true;
    }

    @Override
    public void handle(long l) {

    }

    public void stop() {
        running = false;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed(){
        return this.speed;
    }


}
