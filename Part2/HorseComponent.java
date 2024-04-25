package Part2;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HorseComponent extends JComponent {
    private Horse horse;
    private int trackLength;
    private int horseSize = 50; // Adjust this value to change the size of the horse
    private Image horseImage;
    private String breed;

    public int wins = 0;
    public int losses = 0;
    public boolean racing = false;

    public HorseComponent(Horse horse, int trackLength) {
        this.horse = horse;
        this.trackLength = trackLength;
        breed = "horse"; // Default breed is "Horse

        File file = new File("images/" + breed + ".png"); // Replace "horse_image.png" with the path to your image file
        try {
            horseImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (horseImage != null) {
            int horseX = (int) ((float) horse.getDistanceTravelled() / trackLength * getWidth());
            int horseY = getHeight() / 2;

            // Draw the horse image
            g.drawImage(horseImage, horseX, horseY, horseSize, horseSize, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(trackLength, horseSize * 2); // Adjust the height based on horseSize
    }

    public void moveHorse(double distance) {
        horse.moveForward(distance);
        repaint();
    }

    public boolean hasFallen() {
        return horse.hasFallen();
    }

    public double getConfidence() {
        return horse.getConfidence();
    }

    public void fall() {
        horse.fall();
        if(horse.getConfidence() > 0.1){
            horse.confidence -= 0.1;
        }        
    }
    public void stand(){
        horse.hasFallen = false;
        File file = new File("images/" + breed + ".png");
        try {
            horseImage = ImageIO.read(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Horse getHorse() {
        return horse;
    }

    public int getWins() {
        return wins;
    }
    public int getLosses() {
        return losses;
    }

    public void setSize(int horseSize) {
        this.horseSize = horseSize;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
    public String getBreed() {
        return breed;
    }

    public void win() {
        wins++;
        horse.confidence += 0.1;
    }
    public void lose() {
        if(racing){
            losses++;
        }
    }


    public void setImage(String imagePath) {
        File file = new File(imagePath);
        try {
            horseImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getImage() {
        return horseImage;
    }
}
