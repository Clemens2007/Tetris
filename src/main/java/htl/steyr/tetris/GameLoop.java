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

    public void pause(){
        running = false;
    }

    public void resume(){
        lastTime = 0;
        running = true;
    }

    public void stop(){running = false;}

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public double getSpeed(){
        return this.speed;
    }


    @Override
    public void handle(long now) {
        if (running) {

            if (lastTime == 0) {
                lastTime = now;
                return;
            }

            double dt = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            dt *= speed;

            tetrisGame.update(dt);
            framerate(now);
        }

    }

    public void framerate(long now){
        // Framerate display in console
        if(framerateSwitch) {
            frames++;
            if (now - lastFpsTime >= 1_000_000_000.0) {
                fps = frames;
                frames = 0;
                lastFpsTime = now;


                System.out.print("----\n" + "Memory: " + Runtime.getRuntime().totalMemory()/1_000_000 + "MB" + "\nFPS: " + fps + "\n" + "----\n");
            }
        }
    }

    public void framerateActive(){
        if(framerateSwitch){
            framerateSwitch = false;
        } else if (!framerateSwitch){
            framerateSwitch = true;
        }
    }


}
