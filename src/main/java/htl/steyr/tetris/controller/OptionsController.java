package htl.steyr.tetris.controller;

import htl.steyr.tetris.user.UserSession;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
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
    private List<String> songList = new ArrayList<>();
    private int currentSongIndex = 0;
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {

        loadSettings();
        loadTheme();
        loadSong(currentSongIndex);

        songList.add(getClass().getResource("/htl/steyr/tetris/music/music1.mp3").toExternalForm());
        songList.add(getClass().getResource("/htl/steyr/tetris/music/music2.mp3").toExternalForm());
        songList.add(getClass().getResource("/htl/steyr/tetris/music/music3.mp3").toExternalForm());

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
    }

    // ---------------- SETTINGS ----------------

    private void loadSettings() {

        musicSlider.setValue(prefs.getDouble("musicVolume", 50));
        soundSlider.setValue(prefs.getDouble("soundVolume", 50));

        upKeyButton.setText(prefs.get("UP", "W"));
        downKeyButton.setText(prefs.get("DOWN", "S"));
        leftKeyButton.setText(UserSession.getUserData().getSetting("left").getName());
        rightKeyButton.setText(UserSession.getUserData().getSetting("right").getName());
        holdButton.setText(UserSession.getUserData().getSetting("hold").getName());
        softdropButton.setText(UserSession.getUserData().getSetting("softdrop").getName());
        harddropButton.setText(UserSession.getUserData().getSetting("harddrop").getName());
        rotateLeftButton.setText(UserSession.getUserData().getSetting("rotate_left").getName());
        rotateRightButton.setText(UserSession.getUserData().getSetting("rotate_right").getName());

        holdButton.setText(prefs.get("HOLD", "SHIFT"));
        softdropButton.setText(prefs.get("SOFTDROP", "S"));
        harddropButton.setText(prefs.get("HARDDROP", "SPACE"));
    }

    private void saveSettings() {

        prefs.putDouble("musicVolume", musicSlider.getValue());
        prefs.putDouble("soundVolume", soundSlider.getValue());

        prefs.put("UP", upKeyButton.getText());
        prefs.put("DOWN", downKeyButton.getText());
        UserSession.getUserData().setSetting("left", KeyCode.valueOf(leftKeyButton.getText()));
        UserSession.getUserData().setSetting("right", KeyCode.valueOf(rightKeyButton.getText()));
        UserSession.getUserData().setSetting("hold", KeyCode.valueOf(holdButton.getText()));
        UserSession.getUserData().setSetting("softdrop", KeyCode.valueOf(softdropButton.getText()));
        UserSession.getUserData().setSetting("harddrop", KeyCode.valueOf(harddropButton.getText()));
        UserSession.getUserData().setSetting("rotate_left", KeyCode.valueOf(rotateLeftButton.getText()));
        UserSession.getUserData().setSetting("rotate_right", KeyCode.valueOf(rotateRightButton.getText()));
        UserSession.getUserData().save();

        prefs.put("HOLD", holdButton.getText());
        prefs.put("SOFTDROP", softdropButton.getText());
        prefs.put("HARDDROP", harddropButton.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Gespeichert");
        alert.setHeaderText(null);
        alert.setContentText("Einstellungen wurden gespeichert!");
        alert.showAndWait();
    }

    // ---------------- MUSIC ----------------

    private void loadSong(int index) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        Media media = new Media(songList.get(index));
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        mediaPlayer.setOnEndOfMedia(() -> {
            nextSong();
        });
    }

    public void nextSong() {
        if (songList.isEmpty()) return;
        currentSongIndex = (currentSongIndex + 1) % songList.size();
        loadSong(currentSongIndex);
    }

    public void prevSong() {
        if (songList.isEmpty()) return;
        currentSongIndex = (currentSongIndex - 1 + songList.size()) % songList.size();
        loadSong(currentSongIndex);
    }

    public void musicVolumeSlider(MouseEvent mouseEvent) {
        musicSlider.setMin(0);
        musicSlider.setMax(100);
        musicSlider.setValue(40);

        // passt die Lautstärke an:
        mediaPlayer.volumeProperty().bind(musicSlider.valueProperty());
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
                    getClass().getResource("/stylesheets/darkmode.css").toExternalForm()
            );
            white_darkmode_button.setText("Dark Mode");
        } else {
            scene.getStylesheets().add(
                    getClass().getResource("/stylesheets/whitemode.css").toExternalForm()
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
            listenForKey(button, keyName);
        });
    }

    private void listenForKey(Button button, String keyName) {

        Scene scene = button.getScene();
        if (scene == null) return;

        scene.setOnKeyPressed(event -> {

            KeyCode keyCode = event.getCode();

            button.setText(keyCode.toString());

            prefs.put(keyName, keyCode.toString());

            scene.setOnKeyPressed(null);
        });
    }
}