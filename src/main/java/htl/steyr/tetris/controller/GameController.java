package htl.steyr.tetris.controller;

import htl.steyr.tetris.GameLoop;
import htl.steyr.tetris.TetrisGame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    @FXML
    private AnchorPane root;

    // Visual Game Grid Field + logic layer (movement, collision, usw. )
    private GridPane gameField;
    private Pane gameLayer;
    // StackPane contains those <-
    private StackPane gameStack;

    private TetrisGame tetrisGame;
    private GameLoop loop;

    private static final int ROWS = 20;
    private static final int COLS = 10;
    private static final int CELL_SIZE = 24;

    private Rectangle[][] cells = new Rectangle[ROWS][COLS];
    private boolean[][] board = new boolean[ROWS][COLS];

    // Aktueller Block
    private int[][] shape;
    private int blockRow, blockCol;
    private Color blockColor;

    private String keyString;

    @FXML
    public void initialize() {

        // (GRID + Logic Layer) inside StackPane, inside root setup -->
        gameStack = new StackPane();
        gameField = new GridPane();
        gameLayer = new Pane();

        gameField.setPrefSize(CELL_SIZE*COLS, CELL_SIZE*ROWS);
        gameLayer.setPrefSize(CELL_SIZE*COLS, CELL_SIZE*ROWS);

        gameStack.getChildren().addAll(gameField, gameLayer);
        root.getChildren().add(gameStack);

        System.out.println(gameLayer.getLayoutBounds());
        System.out.println(gameField.getLayoutX());

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Rectangle r = new Rectangle(CELL_SIZE, CELL_SIZE);
                r.setFill(Color.WHITE);
                r.setStroke(Color.LIGHTGRAY);
                r.setStrokeWidth(1);
                cells[row][col] = r;
                gameField.add(r, col, row);
            }
        }

        Platform.runLater(() -> {
            gameStack.layoutYProperty().bind(
                    root.heightProperty().subtract(gameStack.heightProperty()).divide(2)
            );
            gameStack.layoutXProperty().bind(
                    root.widthProperty().subtract(gameStack.widthProperty()).divide(2)
            );
        });

        gameLayer.setStyle("-fx-background-color: blue;");
        gameLayer.setBackground(null);




        // LOGIC -->
        tetrisGame = new TetrisGame(gameLayer, COLS, ROWS);
        tetrisGame.setStartPosition(CELL_SIZE*COLS, CELL_SIZE*ROWS);



        // KEYBINDS -->
        root.sceneProperty().addListener((obs, oldScene, scene) -> {
            if (scene != null) {

                root.setFocusTraversable(true);
                Platform.runLater(() -> root.requestFocus());
                root.setOnKeyPressed(event -> {
                    keyString = event.getCode().toString();
                    if(keyString == "I"){

                    }
                    tetrisGame.move(keyString);
                });
            }
        });


        loop = new GameLoop(tetrisGame) {
            @Override
            public void handle(long now){
                super.handle(now);
            }
        };

        loop.start(); // Toast mit Schinken
    }





}