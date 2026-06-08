package htl.steyr.tetris.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class OptionsController {

    public Button white_darkmode_button;

    @FXML
    private Slider musicSlider;
    @FXML
    private Slider soundSlider;

    @FXML
    private Button saveButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button clearDataButton;

    @FXML
    private Button upKeyButton;
    @FXML
    private Button downKeyButton;
    @FXML
    private Button leftKeyButton;
    @FXML
    private Button rightKeyButton;

    @FXML
    private Button rotateLeftButton;
    @FXML
    private Button rotateRightButton;

    @FXML
    private Button holdButton;
    @FXML
    private Button softdropButton;
    @FXML
    private Button harddropButton;

    //Preferences ist eine eingebaute Java-Klasse zum Speichern von kleinen
    // Konfigurationsdaten (z.B. Einstellungen) direkt auf dem Computer des Users.
    // Die Daten werden automatisch persistent gespeichert (auch nach dem Schließen der App).
    // Du musst also keine eigene Datei schreiben.
    private final Preferences prefs =
            Preferences.userNodeForPackage(OptionsController.class);

    private Button currentKeyButton;

    private boolean darkMode = false;

    @FXML
    public void initialize() {

        loadSettings();
        loadTheme();

        setupKeyButton(upKeyButton, "UP");
        setupKeyButton(downKeyButton, "DOWN");
        setupKeyButton(leftKeyButton, "LEFT");
        setupKeyButton(rightKeyButton, "RIGHT");
        setupKeyButton(rotateLeftButton, "ROTATE_LEFT");
        setupKeyButton(rotateRightButton, "ROTATE_RIGHT");
        setupKeyButton(holdButton, "HOLD");
        setupKeyButton(softdropButton, "SOFTDROP");
        setupKeyButton(harddropButton, "HARDDROP");

        saveButton.setOnAction(e -> saveSettings());
        closeButton.setOnAction(e -> closeWindow());
        clearDataButton.setOnAction(e -> clearAllData());

        white_darkmode_button.setOnAction(e -> toggleTheme());

        saveButton.setDisable(!allKeysAssigned());

    }

    // ---------------- SETTINGS ----------------

    private void loadSettings() {

        musicSlider.setValue(prefs.getDouble("musicVolume", 50));
        soundSlider.setValue(prefs.getDouble("soundVolume", 50));

        upKeyButton.setText(prefs.get("UP", "W"));
        downKeyButton.setText(prefs.get("DOWN", "S"));
        leftKeyButton.setText(prefs.get("LEFT", "A"));
        rightKeyButton.setText(prefs.get("RIGHT", "D"));

        rotateLeftButton.setText(prefs.get("ROTATE_LEFT", "Q"));
        rotateRightButton.setText(prefs.get("ROTATE_RIGHT", "E"));

        holdButton.setText(prefs.get("HOLD", "SHIFT"));
        softdropButton.setText(prefs.get("SOFTDROP", "S"));
        harddropButton.setText(prefs.get("HARDDROP", "SPACE"));
    }

    private void saveSettings() {

        prefs.putDouble("musicVolume", musicSlider.getValue());
        prefs.putDouble("soundVolume", soundSlider.getValue());

        prefs.put("UP", upKeyButton.getText());
        prefs.put("DOWN", downKeyButton.getText());
        prefs.put("LEFT", leftKeyButton.getText());
        prefs.put("RIGHT", rightKeyButton.getText());

        prefs.put("ROTATE_LEFT", rotateLeftButton.getText());
        prefs.put("ROTATE_RIGHT", rotateRightButton.getText());

        prefs.put("HOLD", holdButton.getText());
        prefs.put("SOFTDROP", softdropButton.getText());
        prefs.put("HARDDROP", harddropButton.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Gespeichert");
        alert.setHeaderText(null);
        alert.setContentText("Einstellungen wurden gespeichert!");
        alert.showAndWait();
    }

    // ---------------- THEME ----------------

    private void loadTheme() {
        darkMode = prefs.getBoolean("darkMode", false);
        applyTheme();
    }

    private void toggleTheme() {
        darkMode = !darkMode;
        prefs.putBoolean("darkMode", darkMode);
        applyTheme();
    }

    private void applyTheme() {

        Scene scene = white_darkmode_button.getScene();
        if (scene == null) return;

        scene.getStylesheets().clear();

        if (darkMode) {
            scene.getStylesheets().add(
                    getClass().getResource("/htl/steyr/tetris/stylesheets/darkmode.css").toExternalForm()
            );
            white_darkmode_button.setText("Dark Mode");
        } else {
            scene.getStylesheets().add(
                    getClass().getResource("/htl/steyr/tetris/stylesheets/whitemode.css").toExternalForm()
            );
            white_darkmode_button.setText("Light Mode");
        }
    }

    // ---------------- WINDOW ----------------

    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    // ---------------- RESET ----------------

    private void clearAllData() {

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Warnung");
        confirm.setHeaderText("Alles Änderungen zurücksetzen?");
        confirm.setContentText("Änderungen können nicht mehr rückgängig gemacht werden!");

        confirm.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {
                try {
                    prefs.clear();

                    musicSlider.setValue(50);
                    soundSlider.setValue(50);

                    upKeyButton.setText("W");
                    downKeyButton.setText("S");
                    leftKeyButton.setText("A");
                    rightKeyButton.setText("D");

                    rotateLeftButton.setText("Q");
                    rotateRightButton.setText("E");

                    holdButton.setText("SHIFT");
                    softdropButton.setText("S");
                    harddropButton.setText("SPACE");

                    darkMode = false;
                    applyTheme();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // ---------------- KEYBINDS ----------------

    private void setupKeyButton(Button button, String keyName) {

        button.setOnAction(e -> {
            currentKeyButton = button;
            button.setText("Press Key...");

            // alle anderen Buttons deaktivieren
            setAllKeyButtonsDisabled(true, button);

            listenForKey(button, keyName);
        });
    }

    private void listenForKey(Button button, String keyName) {

        Scene scene = button.getScene();
        if (scene == null) return;

        scene.setOnKeyPressed(event -> {

            String newKey = event.getCode().toString();

            // Prüfen ob Key schon vergeben ist
            if (isKeyAlreadyUsed(keyName, newKey)) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Taste bereits vergeben");
                alert.setHeaderText(null);
                alert.setContentText("Die Taste \"" + newKey + "\" wird bereits verwendet!");
                alert.showAndWait();

                // alten Wert wiederherstellen
                button.setText(prefs.get(keyName, button.getText()));

            } else {
                // neuen Key übernehmen
                button.setText(newKey);
                prefs.put(keyName, newKey);
            }

            // alle Buttons wieder aktivieren
            setAllKeyButtonsDisabled(false, null);

            // Save-Button nur aktivieren, wenn ALLE Keys gesetzt sind
            saveButton.setDisable(!allKeysAssigned());

            scene.setOnKeyPressed(null);
        });
    }



    private boolean isKeyAlreadyUsed(String keyName, String newKey) {

        // Alle Buttons in ein Array packen
        Button[] allButtons = {
                upKeyButton, downKeyButton, leftKeyButton, rightKeyButton,
                rotateLeftButton, rotateRightButton,
                holdButton, softdropButton, harddropButton
        };

        // Alle Key-Namen in gleicher Reihenfolge
        String[] keyNames = {
                "UP", "DOWN", "LEFT", "RIGHT",
                "ROTATE_LEFT", "ROTATE_RIGHT",
                "HOLD", "SOFTDROP", "HARDDROP"
        };

        for (int i = 0; i < allButtons.length; i++) {

            // Diesen Key ignorieren (sonst blockiert er sich selbst)
            if (keyNames[i].equals(keyName)) continue;

            // Prüfen gegen den Text des Buttons (nicht prefs!)
            if (allButtons[i].getText().equals(newKey)) {
                return true;
            }
        }

        return false;
    }
    private boolean allKeysAssigned() {
        return !upKeyButton.getText().equals("Press Key...")
                && !downKeyButton.getText().equals("Press Key...")
                && !leftKeyButton.getText().equals("Press Key...")
                && !rightKeyButton.getText().equals("Press Key...")
                && !rotateLeftButton.getText().equals("Press Key...")
                && !rotateRightButton.getText().equals("Press Key...")
                && !holdButton.getText().equals("Press Key...")
                && !softdropButton.getText().equals("Press Key...")
                && !harddropButton.getText().equals("Press Key...");
    }

    private void setAllKeyButtonsDisabled(boolean disabled, Button except) {

        Button[] all = {
                upKeyButton, downKeyButton, leftKeyButton, rightKeyButton,
                rotateLeftButton, rotateRightButton,
                holdButton, softdropButton, harddropButton, saveButton
        };

        for (Button b : all) {
            if (b != except) b.setDisable(disabled);
        }
    }


}