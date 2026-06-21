package htl.steyr.tetris.controller;

import htl.steyr.tetris.user.UserData;
import htl.steyr.tetris.user.UserSession;
import htl.steyr.tetris.utility.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

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

    private Button currentKeyButton;

    private UserData ud;

    @FXML
    public void initialize() {
        ud = UserSession.getUserData();

        musicSlider.setValue(ud.getVolumeMusic());
        musicSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                htl.steyr.tetris.Music.getInstance().setVolume(newVal.doubleValue())
        );
        soundSlider.setValue(ud.getVolumeSfx());

        setupKeyButton(upKeyButton, "up", ud.getSetting("up"));
        setupKeyButton(downKeyButton, "down", ud.getSetting("down"));
        setupKeyButton(leftKeyButton, "left", ud.getSetting("left"));
        setupKeyButton(rightKeyButton, "right", ud.getSetting("right"));
        setupKeyButton(rotateLeftButton, "rotate_left", ud.getSetting("rotate_left"));
        setupKeyButton(rotateRightButton, "rotate_right", ud.getSetting("rotate_right"));
        setupKeyButton(holdButton, "hold", ud.getSetting("hold"));
        setupKeyButton(softdropButton, "softdrop", ud.getSetting("softdrop"));
        setupKeyButton(harddropButton, "harddrop", ud.getSetting("harddrop"));

        white_darkmode_button.setText(ViewSwitcher.isDarkMode() ? "Dark Mode" : "Light Mode");
        white_darkmode_button.setOnAction(e -> toggleTheme());
        saveButton.setOnAction(e -> saveSettings());
        closeButton.setOnAction(e -> returnToMenu());
        clearDataButton.setOnAction(e -> clearAllData());
        saveButton.setDisable(!allKeysAssigned());

    }

    private void returnToMenu() {
        ud.save();
        ViewSwitcher.switchTo("menu.fxml");
    }

    public void prevSongButtonClicked(ActionEvent actionEvent) {
        htl.steyr.tetris.Music.getInstance().previous();
    }

    public void nextSongButtonClicked(ActionEvent actionEvent) {
        htl.steyr.tetris.Music.getInstance().next();
    }

    public void resetHighscoreClicked(ActionEvent actionEvent) {
        ud.setHighscore(0);
        ud.save();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Highscore zurückgesetzt");
        alert.setHeaderText(null);
        alert.setContentText("Dein Highscore wurde zurückgesetzt!");
        alert.showAndWait();
    }


    private void saveSettings() {
        UserData ud = UserSession.getUserData();
        ud.setVolumeMusic((int) musicSlider.getValue());
        ud.setVolumeSfx((int) soundSlider.getValue());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Gespeichert");
        alert.setHeaderText(null);
        alert.setContentText("Einstellungen wurden gespeichert!");
        alert.showAndWait();
        ud.save();
    }

    private void loadTheme() {
        applyTheme();
    }

    private void toggleTheme() {
        if (ViewSwitcher.isDarkMode()) {
            ViewSwitcher.setLightMode();
        } else {
            ViewSwitcher.setDarkMode();
        }
        applyTheme();
    }

    private void applyTheme() {
        Scene scene = white_darkmode_button.getScene();
        if (scene == null) return;

        scene.getStylesheets().clear();

        if (ViewSwitcher.isDarkMode()) {
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

    private void closeWindow() {
        ud.save();
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void clearAllData() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Warnung");
        confirm.setHeaderText("Alles Änderungen zurücksetzen?");
        confirm.setContentText("Diese Änderung kann nicht mehr rückgängig gemacht werden!");

        confirm.showAndWait().ifPresent(response -> {
            if (response != ButtonType.OK) return;

            UserData ud = UserSession.getUserData();
            ud.setVolumeMusic(50);
            ud.setVolumeSfx(50);
            musicSlider.setValue(50);
            soundSlider.setValue(50);

            ud.setSetting("up", KeyCode.W);
            ud.setSetting("down", KeyCode.A);
            ud.setSetting("left", KeyCode.S);
            ud.setSetting("right", KeyCode.D);
            ud.setSetting("rotate_left", KeyCode.Z);
            ud.setSetting("rotate_right", KeyCode.X);
            ud.setSetting("hold", KeyCode.C);
            ud.setSetting("softdrop", KeyCode.S);
            ud.setSetting("harddrop", KeyCode.SPACE);
            ud.save();

            upKeyButton.setText(KeyCode.W.getName());
            downKeyButton.setText(KeyCode.A.getName());
            leftKeyButton.setText(KeyCode.S.getName());
            rightKeyButton.setText(KeyCode.D.getName());
            rotateLeftButton.setText(KeyCode.Z.getName());
            rotateRightButton.setText(KeyCode.X.getName());
            holdButton.setText(KeyCode.C.getName());
            softdropButton.setText(KeyCode.S.getName());
            harddropButton.setText(KeyCode.SPACE.getName());
        });
    }

    private void setupKeyButton(Button button, String action, KeyCode currentKey) {
        button.setText(currentKey.getName());
        button.setOnAction(e -> {
            currentKeyButton = button;
            button.setText("Press Key...");
            setAllKeyButtonsDisabled(true, button);
            listenForKey(button, action);
        });
        ud.save();
    }

    private void listenForKey(Button button, String action) {
        Scene scene = button.getScene();
        if (scene == null) return;

        scene.setOnKeyPressed(event -> {
            String newKey = event.getCode().toString();

            if (isKeyAlreadyUsed(action, newKey)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Taste bereits vergeben");
                alert.setHeaderText(null);
                alert.setContentText("Die Taste \"" + newKey + "\" wird bereits verwendet!");
                alert.showAndWait();
                button.setText(UserSession.getUserData().getSetting(action).getName());
            } else {
                button.setText(newKey);
                UserSession.getUserData().setSetting(action, event.getCode());
                UserSession.getUserData().save();
            }

            setAllKeyButtonsDisabled(false, null);
            saveButton.setDisable(!allKeysAssigned());
            scene.setOnKeyPressed(null);
        });
    }

    private boolean isKeyAlreadyUsed(String skipAction, String newKey) {
        String[] actions = {"up", "down", "left", "right", "rotate_left", "rotate_right", "hold", "softdrop", "harddrop"};
        UserData ud = UserSession.getUserData();
        for (String a : actions) {
            if (a.equals(skipAction)) continue;
            if (ud.getSetting(a).toString().equals(newKey)) return true;
        }
        return false;
    }

    private boolean allKeysAssigned() {
        Button[] all = {upKeyButton, downKeyButton, leftKeyButton, rightKeyButton,
                rotateLeftButton, rotateRightButton, holdButton, softdropButton, harddropButton};
        for (Button b : all)
            if (b.getText().equals("Press Key...")) return false;
        return true;
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