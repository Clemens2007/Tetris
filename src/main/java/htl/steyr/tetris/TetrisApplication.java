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
        FXMLLoader fxmlLoader = new FXMLLoader(TetrisApplication.class.getResource("fxml/start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        ViewSwitcher.setStage(stage);
        stage.setScene(scene);
        stage.show();


    }
}
