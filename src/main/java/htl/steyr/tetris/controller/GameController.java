package htl.steyr.tetris.controller;

import htl.steyr.tetris.Board;
import htl.steyr.tetris.Pieces;
import htl.steyr.tetris.user.UserData;
import htl.steyr.tetris.user.UserSession;
import htl.steyr.tetris.utility.ViewSwitcher;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameController {

    @FXML
    private GridPane gameField;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label linesLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Pane holdPane;
    @FXML
    private Pane nextPane;

    private static final int CELL_SIZE = 24;

    private final Rectangle[][] cells = new Rectangle[Board.ROWS][Board.COLS];
    private final Board board = new Board();

    private UserData ud;

    private Pieces current;
    private Pieces next;
    private Pieces hold;
    private boolean holdUsed = false;

    private int blockRow, blockCol;

    private Timeline timeline;
    private boolean paused = false;

    private int score = 0;
    private int lines = 0;
    private int level = 1;

    @FXML
    public void initialize() {
        ud = UserSession.getUserData();
        buildGrid();

        next = Pieces.random();
        spawnPiece();

        gameField.sceneProperty().addListener((obs, oldScene, scene) -> {
            if (scene != null) {
                gameField.setFocusTraversable(true);
                Platform.runLater(() -> gameField.requestFocus());
                scene.setOnKeyPressed(this::handleKey);
            }
        });

        startTimeline(600);
        render();
    }

    private void buildGrid() {
        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLS; col++) {
                Rectangle r = new Rectangle(CELL_SIZE, CELL_SIZE);
                r.setFill(Color.WHITE);
                r.setStroke(Color.LIGHTGRAY);
                r.setStrokeWidth(1);
                cells[row][col] = r;
                gameField.add(r, col, row);
            }
        }
    }

    private void startTimeline(double millis) {
        if (timeline != null) timeline.stop();
        timeline = new Timeline(new KeyFrame(Duration.millis(millis), e -> tick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void handleKey(javafx.scene.input.KeyEvent event) {
        if (paused) return;

        KeyCode code = event.getCode();

        if (code == ud.getSetting("left")) {
            move(0, -1);
        } else if (code == ud.getSetting("right")) {
            move(0, 1);
        } else if (code == ud.getSetting("down") || code == ud.getSetting("softdrop")) {
            if (!move(1, 0)) lockPiece();
        } else if (code == ud.getSetting("rotate_left")) {
            rotate(false);
        } else if (code == ud.getSetting("rotate_right")) {
            rotate(true);
        } else if (code == ud.getSetting("hold")) {
            holdCurrent();
        } else if (code == ud.getSetting("harddrop")) {
            hardDrop();
        }

        render();
    }

    private void tick() {
        if (paused) return;
        if (!move(1, 0)) lockPiece();
        render();
    }

    private void spawnPiece() {
        current = next;
        next = Pieces.random();

        blockRow = 0;
        blockCol = Board.COLS / 2 - 2;
        holdUsed = false;

        if (!board.canPlace(current.getShape(), blockRow, blockCol)) {
            gameOver();
        }

        drawPreview(nextPane, next);
    }

    private boolean move(int dRow, int dCol) {
        int newRow = blockRow + dRow;
        int newCol = blockCol + dCol;

        if (board.canPlace(current.getShape(), newRow, newCol)) {
            blockRow = newRow;
            blockCol = newCol;
            lastMoveWasRotate = false;
            return true;
        }
        return false;
    }

    private void rotate(boolean clockwise) {
        int[][] rotated = current.rotated(clockwise);
        if (board.canPlace(rotated, blockRow, blockCol)) {
            current.setShape(rotated);
        }
    }

    private void hardDrop() {
        while (move(1, 0)) {
            // fällt bis zur Kollision
        }
        lockPiece();
    }

    private void holdCurrent() {
        if (holdUsed) return;
        holdUsed = true;

        if (hold == null) {
            hold = current;
            spawnPiece();
        } else {
            Pieces temp = current;
            current = hold;
            hold = temp;
            blockRow = 0;
            blockCol = Board.COLS / 2 - 2;
        }

        drawPreview(holdPane, hold);
    }

    private void lockPiece() {
        board.lock(current.getShape(), blockRow, blockCol, current.getColor());

        int cleared = board.clearFullLines();
        if (cleared > 0) {
            lines += cleared;
            score += cleared * 100 * level;
            level = 1 + lines / 10;

            startTimeline(Math.max(150, 600 - (level - 1) * 50));

            scoreLabel.setText(String.valueOf(score));
            linesLabel.setText(String.valueOf(lines));
            levelLabel.setText(String.valueOf(level));
        }

        spawnPiece();
    }

    private void render() {
        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLS; col++) {
                Color c = board.get(row, col);
                cells[row][col].setFill(c != null ? c : Color.WHITE);
            }
        }

        int[][] shape = current.getShape();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (shape[r][c] == 1) {
                    int boardRow = blockRow + r;
                    int boardCol = blockCol + c;
                    if (boardRow >= 0 && boardRow < Board.ROWS && boardCol >= 0 && boardCol < Board.COLS) {
                        cells[boardRow][boardCol].setFill(current.getColor());
                    }
                }
            }
        }
    }

    private void drawPreview(Pane pane, Pieces piece) {
        pane.getChildren().clear();
        int size = 18;
        int[][] shape = piece.getShape();

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (shape[r][c] == 1) {
                    Rectangle rect = new Rectangle(size, size);
                    rect.setFill(piece.getColor());
                    rect.setX(c * size);
                    rect.setY(r * size);
                    pane.getChildren().add(rect);
                }
            }
        }
    }

    private void gameOver() {
        timeline.stop();
        ud.setScore(score);
        ud.save();
        ViewSwitcher.switchTo("gameover.fxml");
    }

    public void onCloseButtonClicked(ActionEvent actionEvent) {
        ud.save();
        Platform.exit();
    }

    public void onPauseButtonClicked(ActionEvent actionEvent) {
        paused = !paused;
    }
}