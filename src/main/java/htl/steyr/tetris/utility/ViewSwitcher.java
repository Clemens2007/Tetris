package htl.steyr.tetris.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

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
            String fxmlFilePath = "/htl/steyr/tetris/fxml/" + fxml;
            System.out.println("URL: " + ViewSwitcher.class.getResource(fxmlFilePath));
            Parent root = FXMLLoader.load(Objects.requireNonNull(ViewSwitcher.class.getResource(fxmlFilePath)));

            Scene scene = new Scene(root);

            // Stylesheet setzen
            scene.getStylesheets().clear();

            if (darkMode) {
                scene.getStylesheets().add(
                        ViewSwitcher.class.getResource("/htl/steyr/tetris/stylesheets/darkmode.css").toExternalForm()
                );
            } else {
                scene.getStylesheets().add(
                        ViewSwitcher.class.getResource("/htl/steyr/tetris/stylesheets/whitemode.css").toExternalForm()
                );
            }

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FXMLLoader getLoader(String fxml) {
        return new FXMLLoader(ViewSwitcher.class.getResource("/htl/steyr/tetris/fxml/" + fxml));
    }
}
