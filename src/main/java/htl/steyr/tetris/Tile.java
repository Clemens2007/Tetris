package htl.steyr.tetris;


import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

import java.util.Random;


public class Tile extends Group {
    private Block base = new Block(); // basis spawn block, doesnt change
    private Block a = new Block();
    private Block b = new Block();
    private Block c = new Block();


    private double unit = 40;
    private double scale = 0.75;
    private double blockSize = unit * scale;

    private double oldDreh = 0;
    private Rotate rotate = new Rotate();
    private boolean noRotate = false;
    private boolean halfRotate = false;

    public Tile(){


        base.setScale(scale);
        a.setScale(scale);
        b.setScale(scale);
        c.setScale(scale);
        createZ();
        this.getChildren().addAll(base, a, b, c);

        double centerX = base.getBoundsInLocal().getCenterX();
        double centerY = base.getBoundsInLocal().getCenterY();

        rotate.setPivotX(centerX);
        rotate.setPivotY(centerY);

        this.getTransforms().add(rotate);

    }

    public void createT(){
        a.setX(a.getX() + blockSize);
        b.setY(b.getY() + blockSize);
        c.setX(c.getX() - blockSize);
    }

    public void createL(){
        a.setX(a.getX() + blockSize);
        b.setX(b.getX() - blockSize);
        c.setY(c.getY() + blockSize);
        c.setX(c.getX() + blockSize);
    }

    public void createRL(){
        a.setX(a.getX() + blockSize);
        b.setX(b.getX() - blockSize);
        c.setY(c.getY() + blockSize);
        c.setX(c.getX() - blockSize);
    }

    public void createI(){
        halfRotate = true;
        a.setX(a.getX() + blockSize);
        b.setX(b.getX() - blockSize);
        c.setY(c.getY() - blockSize);
    }

    public void createO(){
        noRotate = true;
        a.setX(a.getX() + blockSize);
        b.setY(b.getY() + blockSize);
        c.setX(c.getX() + blockSize);
        c.setY(c.getY() + blockSize);
    }

    public void createZ(){
        halfRotate = true;
        a.setX(a.getX() + blockSize);
        b.setY(b.getY() - blockSize);
        c.setX(c.getX() - blockSize);
        c.setY(c.getY() - blockSize);
    }

    public void createRZ(){
        halfRotate = true;
        a.setX(a.getX() + blockSize);
        b.setY(b.getY() + blockSize);
        c.setX(c.getX() + blockSize);
        c.setY(c.getY() - blockSize);
    }

    public void randomTile(String oldTile){
        Random rand = new Random();
        int random = rand.nextInt(7);

        switch (random) {
            case 0: {
                createT();
                break;
            }
            case 1: {
                createL();
                break;
            }
            case 2: {
                createRL();
                break;
            }
            case 3: {
                createI();
                break;
            }
            case 4: {
                createO();
                break;
            }
            case 5: {
                createZ();
                break;
            }
            case 6: {
                createRZ();
                break;
            }
        };
    }

    public double getScale(){
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void rotato(double dreh){
        if(!noRotate){
            if(halfRotate && oldDreh != 0){
                rotate.setAngle(0);
                oldDreh = 0;
                return;
            }

            if(halfRotate && dreh < 0){
                rotate.setAngle(0 - dreh);
                oldDreh = dreh;
                return;
            }

            rotate.setAngle(oldDreh + dreh);
            oldDreh = oldDreh + dreh;
        }
    }

}
