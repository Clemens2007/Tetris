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

    private int x;
    private int y;
    private double oldDreh = 0;
    private Rotate rotate = new Rotate();

    private Point3D startpoint = new Point3D(10, 40, 0);

    public Tile(){
        double centerX = a.getBoundsInLocal().getCenterX();
        double centerY = a.getBoundsInLocal().getCenterY();

        rotate.setPivotX(centerX);
        rotate.setPivotY(centerY);

        this.getTransforms().add(rotate);


        System.out.println(a.getBoundsInLocal());
        System.out.println(b.getBoundsInParent());

        a.setX(a.getX() - 40);
        b.setX(b.getX() + 40);
        c.setY(c.getY() + 40);

        this.getChildren().add(a);
        this.getChildren().add(b);
        this.getChildren().add(c);
    }



    public void createT(){

        a.setX(a.getX() + 40);

        b.setY(b.getY() + 40);

        c.setX(c.getX() - 40);

    }

    public void createL(){

        a.setX(a.getX() + 40);

        b.setX(b.getX() - 40);

        c.setY(c.getY() + 40);

        c.setX(c.getX() + 40);

    }

    public void createRL(){

        a.setX(a.getX() + 40);

        b.setX(b.getX() - 40);

        c.setY(c.getY() + 40);

        c.setX(c.getX() - 40);

    }

    public void createI(){

        a.setX(a.getX() + 40);

        b.setX(b.getX() - 40);

        c.setY(c.getY() - 40);

    }

    public void createO(){

        a.setX(a.getX() + 40);

        b.setY(b.getY() + 40);

        c.setX(c.getX() + 40);

        c.setY(c.getY() + 40);

    }

    public void createZ(){

        a.setX(a.getX() + 40);

        b.setY(b.getY() - 40);

        c.setX(c.getY() - 40);

        c.setY(c.getY() - 40);

    }

    public void createRZ(){

        a.setX(a.getX() + 40);

        b.setY(b.getY() + 40);

        c.setX(c.getY() + 40);

        c.setY(c.getY() - 40);

    }

    public void randomTile(){

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

        }

        this.getChildren().addAll(base, a, b, c);

    }


    public void moveVertic(int n){
        this.setLayoutX(this.getLayoutX() - n);
    }

    public void moveHorizon(int n){
        this.setLayoutY(this.getLayoutY() - n);
    }

    public void rotato(double dreh){
        rotate.setAngle(oldDreh + dreh);
        oldDreh = oldDreh + dreh;
    }

}