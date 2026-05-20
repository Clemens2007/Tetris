package htl.steyr.tetris.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewSwitcher {

    private static Stage stage;

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void switchTo(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewSwitcher.class.getResource(fxml));
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Wenn man den Controller noch braucht (z.B. Username übergeben)
    public static FXMLLoader getLoader(String fxml) {
        return new FXMLLoader(ViewSwitcher.class.getResource(fxml));
    }
}