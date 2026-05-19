package htl.steyr.tetris;


import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;


public class Tile extends Group {

    private int x;
    private int y;
    private double oldDreh = 0;
    private Block base = new Block();
    private Rotate rotate = new Rotate();

    private Point3D startpoint = new Point3D(10, 40, 0);

    public Tile(){

        Block a = new Block();
        Block b = new Block();
        Block c = new Block();
        Block d = new Block();
        Block e = new Block();
        Block f = new Block();

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
        d.setY(d.getY() + 80);
        e.setY(e.getY() - 120);

        this.getChildren().add(a);
        this.getChildren().add(b);
        this.getChildren().add(c);
        this.getChildren().add(d);
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
