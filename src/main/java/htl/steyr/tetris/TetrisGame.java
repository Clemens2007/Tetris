

package htl.steyr.tetris;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;



public class TetrisGame {

    @FXML
    private int x = 0;
    private AnchorPane root;
    private Tile tile = new Tile();
    private String keyString;


    public TetrisGame(AnchorPane root){
        this.root = root;
        root.getChildren().add(tile);

        root.sceneProperty().addListener((obs, oldScene, scene) -> {
            if (scene != null) {
                root.setFocusTraversable(true);
                Platform.runLater(() -> root.requestFocus());
                root.setOnKeyPressed(event -> {
                    keyString = event.getCode().toString();
                    if(keyString == "I"){

                    }
                    //System.out.println(keyString);
                    rotate(keyString);
                });
            }
        });
    }

    public void tetrahedrionShape(){

        if (x==0){
            Block block = new Block();
            root.getChildren().add(block);
            x = 1;
        }
    }


    public void update(double dt){

    }


    public void rotate(String keybind){
        switch (keybind){
            case "LEFT": {
                System.out.println("wwwwuuuuuush");
                tile.setRotate(tile.getRotate() - 90);
                break;
            }
            case "RIGHT": {
                tile.setRotate(tile.getRotate() + 90);
                System.out.println("wwwwaaaaaaaaa");
                break;
            }

        }
    }

    public void getKey(String key){
        this.keyString = key;
    }


}
