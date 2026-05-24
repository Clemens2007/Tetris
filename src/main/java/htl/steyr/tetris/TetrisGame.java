package htl.steyr.tetris;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class TetrisGame {
    private AnchorPane root;
    private Block currentBlock;
    private double fallTimer = 0;
    private final double fallSpeed = 0.5;

    public TetrisGame(AnchorPane root){
        this.root = root;

        spawnBlock();

        root.sceneProperty().addListener((obs, oldScene, scene) -> {

            if (scene != null) {
                root.setFocusTraversable(true);

                Platform.runLater(
                        () -> root.requestFocus()
                );
            }
        });
    }

    public void update(double dt){
        fallTimer += dt;

        if (fallTimer >= fallSpeed) {
            fallTimer = 0;
        }
    }

    private void spawnBlock() {
        currentBlock = Block.randomBlock();

        currentBlock.setLayoutX(200);
        currentBlock.setLayoutY(0);

        root.getChildren().add(currentBlock);
    }

}