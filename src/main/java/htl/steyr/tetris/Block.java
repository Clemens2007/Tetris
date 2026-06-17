package htl.steyr.tetris;

import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {

    public Block(){
        setWidth(40);
        setHeight(40);
    }

    public void setScale(double scale){
        setWidth(getWidth() * scale);
        setHeight(getHeight() * scale);
    }






}
