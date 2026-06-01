module htl.steyr.tetris {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;


    opens htl.steyr.tetris to javafx.fxml;
    exports htl.steyr.tetris;
}