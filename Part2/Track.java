package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Track extends JPanel {
    public int trackid;
    private int trackLength;
    private Color color = new Color(0xb33c34);
    private HorseComponent horseComponent;
    public static boolean raceEnded = true;

    public Track(int trackLength, int trackHeight, int trackid) {
        setBackground(color);
        this.trackLength = trackLength;
        this.trackid = trackid;
        setPreferredSize(new Dimension(trackLength, trackHeight));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        setLayout(new BorderLayout());

        String[] horseNames = {"Thunder", "Lightning", "Joolpool", "Shadow", "Spirit", "Storm", "Blaze", "Midnight", "Misty", "Whisper", "Whirlwind", "Bolt", "Flash", "Thunderbolt", "Tornado", "Hurricane", "Typhoon", "Cyclone", "Twister", "Gale", "Breeze", "Zephyr", "Chinook", "Sirocco", "Monsoon", "Maelstrom"};
        // Create a Random object
        Random random = new Random();
        // Generate random index to pick a name from the array
        int randomIndex = random.nextInt(horseNames.length);
        double randomConfidence = Math.round((0.2 + (0.8 - 0.2) * random.nextDouble()) * 100.0) / 100.0;

        Horse horse = new Horse('P', horseNames[randomIndex], randomConfidence);
        horseComponent = new HorseComponent(horse, 800);
        add(horseComponent, BorderLayout.WEST);
    }

    public int getTrackId() {
        return trackid;
    }

    public void setColor(Color color) {
        setBackground(color);
        repaint(); // Repaint the track to reflect the color change
    }

    public void setHorse(HorseComponent horse) {
        this.horseComponent = horse;
    }

    public HorseComponent getHorseComponent() {
        return horseComponent;
    }

    public void hideHorse() {
        horseComponent.setVisible(false);
    }
    public void showHorse() {
        horseComponent.setVisible(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calculate the height of each line
        int lineHeight = 100 / 20;

        // Set the color to white for the first line
        g.setColor(Color.WHITE);

        
        for(int j=0; j<2; j++){
            // Draw the finish line pattern
            for (int i = 0; i < 40; i++) {
                // Calculate the y-coordinate of the line
                int y = (i + 1) * lineHeight;
                
                // Draw the line
                g.fillRect(trackLength - 20 - (j*15), y-((j+1)*4), 16, 40);

                // Change the color for the next line
                g.setColor(g.getColor().equals(Color.WHITE) ? Color.BLACK : Color.WHITE);
            }
        }
    }

    public void resetTrack() {
        raceEnded = true; // Ensure race is stopped
        horseComponent.getHorse().goBackToStart();
        horseComponent.stand();
        horseComponent.getHorse().setHasFallen(false); // Reset fallen state

        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                Icon icon = ((JLabel) component).getIcon();
                if (icon != null && icon.toString().equals("images/first.png")) {
                    remove(component);
                    revalidate(); // Ensure the panel is laid out again after component removal
                    repaint(); // Repaint the panel to reflect the changes
                    break; // Stop the loop once the gold medal is found and removed
                }
            }
        }
    }

    public void startRace() {
        raceEnded = false;
        horseComponent.racing = true;
        horseComponent.getHorse().goBackToStart(); // Reset horse position

        // Create a timer to move the horse periodically
        Timer timer;
        // Create a timer object with a delay of 50 milliseconds (adjustable)
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!raceEnded) {
                    moveHorse();
                    if (checkFinishLine()) {
                        raceEnded = true;
                        ((Timer) e.getSource()).stop();
                    }
                } else {
                    ((Timer) e.getSource()).stop();
                    if (!horseComponent.getHorse().hasFallen()) {
                        horseComponent.lose();
                        
                        horseComponent.racing = false;
                    }
                }
            }
        });

        // Start the timer
        timer.start();
    }

    private void moveHorse() {
        // If the horse has fallen, it cannot move
        if (!horseComponent.hasFallen()) {
            // The horse always moves forward, but the distance depends on confidence
            // Higher confidence means the horse moves farther
            double moveProbability = Math.random();
            double moveDistance = moveProbability * horseComponent.getConfidence();
            horseComponent.moveHorse(moveDistance);
        
            // The probability that the horse will fall depends on confidence,
            // but it's less common than moving forward
            // Adjust the fall probability based on confidence, but make it less common
            double fallProbability = 0.05 * (0.1*Math.pow(horseComponent.getConfidence(), 2));
            if (Math.random() < fallProbability) {
                horseComponent.fall();
                String breed = horseComponent.getBreed();
                horseComponent.setImage("images/" + breed + "fall.png");
            }
        }
        else {
            horseComponent.fall();
            String breed = horseComponent.getBreed();
            horseComponent.setImage("images/" + breed + "fall.png");
            horseComponent.lose();
            horseComponent.racing = false;
        }
    }

    public boolean checkFinishLine() {
        int finishLineX = getWidth() - 50; // Assuming the finish line is 50 pixels wide
        int horseX = (int) ((float) horseComponent.getHorse().getDistanceTravelled() / 490 * getWidth());

        if (horseX >= finishLineX) {
            raceEnded = true;

            JLabel goldMedal = new JLabel(new ImageIcon("images/first.png"));
            goldMedal.setSize(new Dimension(100, 100));
            add(goldMedal, BorderLayout.EAST);

            horseComponent.win();
            horseComponent.racing = false;

            // Notify the race manager that the race has ended
            RaceManager raceManager = new RaceManager();
            raceManager.raceEnded(this);

            BettingPanel.recieveWinningTrack(raceManager);

            System.out.println("Race ended!");

            return true;
        }

        return false;
    }
}


