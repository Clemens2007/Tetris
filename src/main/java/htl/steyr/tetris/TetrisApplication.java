package htl.steyr.tetris;

import htl.steyr.tetris.utility.ViewSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TetrisApplication extends Application {
    @Override
    public void start(Stage stage) {
        // Fensterstil muss VOR dem ersten stage.show() gesetzt werden, sonst Exception
        stage.initStyle(StageStyle.UNDECORATED); // kein Betriebssystem-Rahmen/Titelleiste
        stage.setWidth(900);
        stage.setHeight(700);
        stage.setResizable(false); // feste Fenstergröße, damit kein Layout zerschossen wird

        stage.setTitle("Tetris");
        ViewSwitcher.setStage(stage);
        ViewSwitcher.switchTo("start.fxml");
        stage.show();
    }
}
