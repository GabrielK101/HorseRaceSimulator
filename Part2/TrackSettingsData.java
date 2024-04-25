package Part2;

import java.awt.Color;

public class TrackSettingsData {
    private Color trackColor;
    private int id;
    private String horsePath;
    private String breed;
    private String name;

    public Color getTrackColor() {
        return trackColor;
    }

    public int getId() {
        return id;
    }

    public String getHorsePath() {
        return horsePath;
    }

    public String getBreed() {
        return breed;
    }

    public String getName() {
        return name;
    }
    

    public void setTrackColor(int id, Color trackColor, String breed, String name) {
        this.trackColor = trackColor;
        this.id = id;
        this.horsePath = "images/" + breed + ".png";
        this.breed = breed;
        this.name = name;
    }
}
