package htl.steyr.tetris.user;

import com.google.gson.*;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class UserData {

    private String username;
    private String passwordHash;
    private List<Setting> settings;
    private int volumeMusic;
    private int volumeSfx;

    public UserData(String username, String password, boolean isNew) {
        if (isNew) {
            if (Files.exists(Paths.get("users", username + ".txt"))) {
                throw new IllegalArgumentException("User '" + username + "' existiert bereits");
            }
            this.username = username;
            this.passwordHash = Hasher.hashText(password);
            this.settings = getDefaultSettings();
            this.volumeMusic = 50;
            this.volumeSfx = 50;
            save();
        } else {
            load(username);
        }
        UserSession.setUserData(this);
    }

    public void save() {
        JsonObject json = new JsonObject();
        json.addProperty("password", passwordHash);
        JsonObject s = new JsonObject();
        settings.forEach(setting -> s.addProperty(setting.setting(), setting.replacement().name()));
        json.add("settings", s);
        json.addProperty("volumeMusic", volumeMusic);
        json.addProperty("volumeSfx", volumeSfx);

        Path file = Paths.get("users", username + ".txt");
        try {
            Files.createDirectories(file.getParent());
            Files.writeString(file, new GsonBuilder().setPrettyPrinting().create().toJson(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load(String username) {
        this.username = username;

        try {
            JsonObject json = JsonParser.parseString(
                    Files.readString(Paths.get("users", username + ".txt"))
            ).getAsJsonObject();

            passwordHash = json.get("password").getAsString();
            volumeMusic = json.get("volumeMusic").getAsInt();
            volumeSfx = json.get("volumeSfx").getAsInt();

            settings = new ArrayList<>();
            json.getAsJsonObject("settings").entrySet().forEach(e -> settings.add(new Setting(e.getKey(), KeyCode.valueOf(e.getValue().getAsString()))));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSetting(String action, KeyCode key) {
        for (int i = 0; i < settings.size(); i++) {
            if (settings.get(i).setting().equals(action)) {
                settings.set(i, new Setting(action, key));
                return;
            }
        }
    }

    public KeyCode getSetting(String action) {
        return settings.stream()
                .filter(s -> s.setting().equals(action))
                .map(Setting::replacement)
                .findFirst()
                .orElse(null);
    }

    private List<Setting> getDefaultSettings() {
        return new ArrayList<>(List.of(
                new Setting("up", KeyCode.UP),
                new Setting("down", KeyCode.DOWN),
                new Setting("left", KeyCode.LEFT),
                new Setting("right", KeyCode.RIGHT),
                new Setting("rotate_left", KeyCode.Z),
                new Setting("rotate_right", KeyCode.X),
                new Setting("hold", KeyCode.C),
                new Setting("softdrop", KeyCode.S),
                new Setting("harddrop", KeyCode.SPACE)
        ));
    }

    public void printData(){
        System.out.println("username = " + username);
        System.out.println("Passworthash = " + passwordHash);
        System.out.println("SFX Volume % = " + volumeSfx);
        System.out.println("Music Volume % = " + volumeMusic);
    }

    public String getUsername(){
        return username;
    }
    public String getPasswordHash(){
        return passwordHash;
    }
    public int getVolumeMusic(){
        return volumeMusic;
    }
    public void setVolumeMusic(int num){
        volumeMusic = num;
        save();
    }
    public int getVolumeSfx(){
        return volumeSfx;
    }
    public void setVolumeSfx(int num){
        volumeSfx = num;
        save();
    }
}