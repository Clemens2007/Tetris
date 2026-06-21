package htl.steyr.tetris;

import htl.steyr.tetris.utility.ViewSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;

public class TetrisApplication extends Application {
    @Override
    public void start(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setWidth(900);
        stage.setHeight(700);
        stage.setResizable(false);

        stage.setTitle("Tetris");
        ViewSwitcher.setStage(stage);
        ViewSwitcher.switchTo("start.fxml");
        stage.show();
    }
}
