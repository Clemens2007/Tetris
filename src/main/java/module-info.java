module htl.steyr.tetris {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;
    requires com.google.gson;
    requires java.sql;


    opens htl.steyr.tetris to javafx.fxml;
    exports htl.steyr.tetris;
    exports htl.steyr.tetris.controller;
    opens htl.steyr.tetris.controller to javafx.fxml;
}