package htl.steyr.tetris;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {

    private static Music instance;

    private final String[] tracks = {
            "/htl/steyr/tetris/music/music1.mp3",
            "/htl/steyr/tetris/music/music2.mp3",
            "/htl/steyr/tetris/music/music3.mp3"
    };

    private int currentTrack = 0;
    private MediaPlayer player;
    private double volume = 0.5;
    private boolean started = false;

    private Music() {
    }

    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

    public void start() {
        if (started) return;
        started = true;
        play();
    }

    private void play() {
        if (player != null) {
            player.stop();
        }

        Media media = new Media(getClass().getResource(tracks[currentTrack]).toExternalForm());
        player = new MediaPlayer(media);
        player.setVolume(volume);
        player.setOnEndOfMedia(this::next);
        player.play();
    }

    public void next() {
        currentTrack = (currentTrack + 1) % tracks.length;
        play();
    }

    public void previous() {
        currentTrack = (currentTrack - 1 + tracks.length) % tracks.length;
        play();
    }

    public void setVolume(double percent) {
        this.volume = percent / 100.0;
        if (player != null) {
            player.setVolume(volume);
        }
    }
}