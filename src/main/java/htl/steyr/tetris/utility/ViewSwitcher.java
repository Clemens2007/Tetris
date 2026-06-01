package htl.steyr.tetris.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewSwitcher {

    private static Stage stage;

    // Dark/Light Mode speichern
    private static boolean darkMode = false;

    public static void setStage(Stage s) {
        stage = s;
    }

    // Darkmode aktivieren
    public static void setDarkMode() {
        darkMode = true;
    }

    // Lightmode aktivieren
    public static void setLightMode() {
        darkMode = false;
    }

    // Abfragen falls nötig
    public static boolean isDarkMode() {
        return darkMode;
    }

    public static void switchTo(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewSwitcher.class.getResource(fxml));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            // Stylesheet setzen
            scene.getStylesheets().clear();

            if (darkMode) {
                scene.getStylesheets().add(
                        ViewSwitcher.class.getResource("/stylesheets/darkmode.css").toExternalForm()
                );
            } else {
                scene.getStylesheets().add(
                        ViewSwitcher.class.getResource("/stylesheets/whitemode.css").toExternalForm()
                );
            }

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FXMLLoader getLoader(String fxml) {
        return new FXMLLoader(ViewSwitcher.class.getResource(fxml));
    }
}
