package htl.steyr.tetris;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;


public class Tile extends Group {
    private double oldDreh = 0;
    private Rotate rotate = new Rotate();

    public Tile() {

    }
/**
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
**/
}
