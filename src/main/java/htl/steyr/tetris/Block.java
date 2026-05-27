package htl.steyr.tetris;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import java.util.Random;

public class Block extends Group {
    public static final int TILE_SIZE = 40;

    private Block() {

    }

    private void addTile(int x, int y, Color color) {
        Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE, color);
        tile.setStroke(Color.BLACK);

        tile.setX(x * TILE_SIZE);
        tile.setY(y * TILE_SIZE);

        getChildren().add(tile);
    }

    public static Block createI() {
        Block block = new Block();

        block.addTile(0, 0, Color.CYAN);
        block.addTile(1, 0, Color.CYAN);
        block.addTile(2, 0, Color.CYAN);
        block.addTile(3, 0, Color.CYAN);

        return block;
    }

    public static Block createO() {
        Block block = new Block();

        block.addTile(0, 0, Color.YELLOW);
        block.addTile(1, 0, Color.YELLOW);
        block.addTile(0, 1, Color.YELLOW);
        block.addTile(1, 1, Color.YELLOW);

        return block;
    }

    public static Block createT() {
        Block block = new Block();

        block.addTile(1, 0, Color.PURPLE);
        block.addTile(0, 1, Color.PURPLE);
        block.addTile(1, 1, Color.PURPLE);
        block.addTile(2, 1, Color.PURPLE);

        return block;
    }

    public static Block createZ() {
        Block block = new Block();

        block.addTile(0, 0, Color.RED);
        block.addTile(1, 0, Color.RED);
        block.addTile(1, 1, Color.RED);
        block.addTile(2, 1, Color.RED);

        return block;
    }

    public static Block createL() {
        Block block = new Block();

        block.addTile(2, 0, Color.ORANGE);
        block.addTile(0, 1, Color.ORANGE);
        block.addTile(1, 1, Color.ORANGE);
        block.addTile(2, 1, Color.ORANGE);

        return block;
    }

    public static Block randomBlock() {
        Random random = new Random();

        return switch (random.nextInt(7)) {
            case 0 -> createI();
            case 1 -> createO();
            case 2 -> createT();
            case 3 -> createZ();
            default -> createL();
        };
    }
}