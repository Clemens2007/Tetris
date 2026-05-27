package htl.steyr.tetris;


import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;

import java.util.Random;


public class Tile extends Group {

    private int x;
    private int y;
    private double oldDreh = 0;
    private double scale = 1;
    private Rotate rotate = new Rotate();
    private boolean noRotate = false;
    private boolean halfRotate = false;


    private Block base = new Block(); // basis spawn block, doesnt change
    private Block a = new Block();
    private Block b = new Block();
    private Block c = new Block();

    private Point3D startpoint = new Point3D(10, 40, 0);

    public Tile(){

        createRZ();
        this.getChildren().addAll(base, a, b, c);

        double centerX = base.getBoundsInLocal().getCenterX();
        double centerY = base.getBoundsInLocal().getCenterY();

        rotate.setPivotX(centerX);
        rotate.setPivotY(centerY);

        this.getTransforms().add(rotate);
    }

    public void moveVertic(int n){
        this.setLayoutX(this.getLayoutX() - n);
    }

    public void moveHorizon(int n){
        this.setLayoutY(this.getLayoutY() - n);
    }

    public void rotato(double dreh){
        if(!noRotate){
            if(halfRotate && oldDreh!=0){
                rotate.setAngle(0);
                oldDreh = 0;
                return;
            }
            rotate.setAngle(oldDreh + dreh);
            oldDreh = oldDreh + dreh;
        }
    }

    public void setDown(){
        /**
         * here a while() that repeatedly checks collision, and sets the tile down one measure of a Block at a time;
          */
    }

    public double getScale(){
        System.out.println(base.getLayoutBounds().getWidth());
        return base.getLayoutBounds().getWidth();
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void createT(){
        a.setX(a.getX() + (40 * scale));
        b.setY(b.getY() + (40 * scale));
        c.setX(c.getX() - (40 * scale));
    }

    public void createL(){
        a.setX(a.getX() + (40 * scale));
        b.setX(b.getX() - (40 * scale));
        c.setY(c.getY() + (40 * scale));
        c.setX(c.getX() + (40 * scale));
    }

    public void createRL(){
        a.setX(a.getX() + (40 * scale));
        b.setX(b.getX() - (40 * scale));
        c.setY(c.getY() + (40 * scale));
        c.setX(c.getX() - (40 * scale));
    }

    public void createI(){
        halfRotate = true;
        a.setX(a.getX() + (40 * scale));
        b.setX(b.getX() - (40 * scale));
        c.setX(c.getX() - (80 * scale));
    }

    public void createO(){
        noRotate = true;
        a.setX(a.getX() + (40 * scale));
        b.setY(b.getY() + (40 * scale));
        c.setX(c.getX() + (40 * scale));
        c.setY(c.getY() + (40 * scale));
    }

    public void createZ(){
        halfRotate = true;
        a.setX(a.getX() + (40 * scale));
        b.setY(b.getY() - (40 * scale));
        c.setX(c.getX() - (40 * scale));
        c.setY(c.getY() - (40 * scale));
    }

    public void createRZ(){
        halfRotate = true;
        a.setX(a.getX() + (40 * scale));
        b.setY(b.getY() + (40 * scale));
        c.setX(c.getX() + (40 * scale));
        c.setY(c.getY() - (40 * scale));
    }

    public void randomTile(String oldTile){
        Random rand = new Random();
        int random = rand.nextInt(7);
        String currentTile = " ";

        switch (random) {
            case 0: {
                createT();
                currentTile = "T";
                break;
            }
            case 1: {
                createL();
                currentTile = "L";
                break;
            }
            case 2: {
                createRL();
                currentTile = "RL";
                break;
            }
            case 3: {
                createI();
                currentTile = "I";
                break;
            }
            case 4: {
                createO();
                currentTile = "O";
                break;
            }
            case 5: {
                createZ();
                currentTile = "Z";
                break;
            }
            case 6: {
                createRZ();
                currentTile = "RZ";
                break;
            }
        }

        System.out.println(currentTile);
    }




}
