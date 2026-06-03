package htl.steyr.tetris.controller;

import htl.steyr.tetris.utility.ViewSwitcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StartController {

    @FXML private Button closeButton;
    @FXML private Button okButton;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;


    public void onClosedButtonClicked(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onOKButtonClicked(ActionEvent actionEvent) {
        //@ToDo Logik
        ViewSwitcher.switchTo("menu");
    }
}