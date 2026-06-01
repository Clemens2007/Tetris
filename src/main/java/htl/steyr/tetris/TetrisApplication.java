package htl.steyr.tetris;

import htl.steyr.tetris.utility.ViewSwitcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TetrisApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewSwitcher.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(TetrisApplication.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/stylesheets/darkmode.css").toExternalForm());
        stage.setTitle("Hello!");
        ViewSwitcher.setDarkMode();
        stage.setScene(scene);
        stage.show();


    }
}
