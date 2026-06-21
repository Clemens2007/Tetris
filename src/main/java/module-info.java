module htl.steyr.tetris {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;
    requires com.google.gson;
    requires java.sql;
    requires java.prefs;
    requires javafx.media;
    requires java.desktop;

    opens htl.steyr.tetris to javafx.fxml;
    exports htl.steyr.tetris;

    exports htl.steyr.tetris.controller;
    opens htl.steyr.tetris.controller to javafx.fxml;

    exports htl.steyr.tetris.user;
    opens htl.steyr.tetris.user to javafx.fxml;

    exports htl.steyr.tetris.utility;
    opens htl.steyr.tetris.utility to javafx.fxml;

    exports htl.steyr.tetris.highscore_management;
    opens htl.steyr.tetris.highscore_management to javafx.fxml;
}