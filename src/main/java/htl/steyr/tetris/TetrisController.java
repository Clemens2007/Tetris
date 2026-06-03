package htl.steyr.tetris;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TetrisController {

    public Slider musicSlider;
    private List<String> songList = new ArrayList<>();
    private int currentSongIndex = 0;
    private MediaPlayer mediaPlayer;
    @FXML
    private AnchorPane root;
    private TetrisGame tetrisGame;
    private GameLoop loop;

    public void initialize(){
        tetrisGame = new TetrisGame(root);

        loop = new GameLoop(tetrisGame);

        loop.start();



        loop = new GameLoop(tetrisGame) {
            @Override
            public void handle(long now){
                super.handle(now);
            }
        };

        songList.add(getClass().getResource("/htl/steyr/tetris/music/music1.mp3").toExternalForm());
        songList.add(getClass().getResource("/htl/steyr/tetris/music/music2.mp3").toExternalForm());
        songList.add(getClass().getResource("/htl/steyr/tetris/music/music3.mp3").toExternalForm());

        loadSong(currentSongIndex);

        //loop.start(); // Toast mit Schinken
    }

    // Slider für Lautstärke - diese Funktion an den Slider anbinden wenn die fxml fertig ist
    public void musicVolumeSlider(MouseEvent mouseEvent) {
        musicSlider.setMin(0);
        musicSlider.setMax(100);
        musicSlider.setValue(40);

        // passt die Lautstärke an:
        mediaPlayer.volumeProperty().bind(musicSlider.valueProperty());
    }

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

    // an die OnClicked-Funktion von einem Button anlegen wenn die fxml fertig ist
    public void nextSong() {
        if (songList.isEmpty()) return;
        currentSongIndex = (currentSongIndex + 1) % songList.size();
        loadSong(currentSongIndex);
    }

    // an die OnClicked-Funktion von einem Button anlegen wenn die fxml fertig ist
    public void prevSong() {
        if (songList.isEmpty()) return;
        currentSongIndex = (currentSongIndex - 1 + songList.size()) % songList.size();
        loadSong(currentSongIndex);
    }
}
