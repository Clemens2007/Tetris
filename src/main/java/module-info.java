module htl.steyr.tetris {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;
    requires com.google.gson;


    opens htl.steyr.tetris to javafx.fxml;
    exports htl.steyr.tetris;
}