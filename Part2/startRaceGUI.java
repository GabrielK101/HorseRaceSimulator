package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class startRaceGUI {
    private static int numLanes = 3;
    private static int trackLength = 500;
    private static JPanel trackPanel;
    private static JPanel mainPanel;
    private static BettingPanel bettingPanel;
    private static JFrame frame;
    private static ArrayList<Track> tracks = new ArrayList<>();

    public static void main(String[] args) {
        frame = new JFrame("Horse Race");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setResizable(true);
        frame.setBackground(new Color(0x009957)); // Set the background color for the entire window

        // Create a main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0x326b8c)); // Set the background color for the main panel



        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.black);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);


        String[] laneOptions = {"2", "3", "4", "5"}; // Available lane options
        JComboBox<String> laneNumberDropdown = new JComboBox<>(laneOptions);
        laneNumberDropdown.setSelectedIndex(1); // Select default (index 0)
        buttonPanel.add(laneNumberDropdown);
        JButton laneNumberButton = new JButton("Set Lane Number");
        buttonPanel.add(laneNumberButton);

        laneNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) laneNumberDropdown.getSelectedItem();
                int newNumLanes = Integer.parseInt(selectedValue); // Get the selected number of lanes

                // Update the number of lanes
                updateNumLanes(newNumLanes);
            }
        });

        // Initialize tracks
        initializeTracks(numLanes);

        // Create track panel
        trackPanel = createRacePanel();
        mainPanel.add(trackPanel, BorderLayout.WEST);

        JButton startButton = new JButton("Start Race");
        startButton.setSize(startButton.getPreferredSize());
        startButton.setBackground(Color.green);
        startButton.setForeground(Color.black);
        buttonPanel.add(startButton);

        // Add ActionListener to the start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setText("New Race");
                for (Track track : tracks) {
                    track.resetTrack();
                    track.startRace();
                }
        
                Timer endRaceTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (allHorsesHaveFallen()) {
                            ((Timer) e.getSource()).stop(); // Stop the timer
                            JOptionPane.showMessageDialog(frame, "All horses have fallen. Race ended.");
                        }
                    }
                });
                endRaceTimer.start(); // Start the timer
            }
        });


        bettingPanel = new BettingPanel(numLanes, tracks);


        // Add the main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(bettingPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Initialize tracks
    private static void initializeTracks(int numLanes) {
        tracks.clear(); // Clear existing tracks
        for (int i = 0; i < numLanes; i++) {
            Track track = new Track(trackLength, 30, i);
            tracks.add(track);
        }
    }

    // Update number of lanes
    private static void updateNumLanes(int newNumLanes) {
        numLanes = newNumLanes;
        initializeTracks(numLanes); // Reinitialize tracks
        updateTrackPanel(); // Update track panel
        updateBettingsPanel(); // Update betting panel
    }

    // Create track panel
    private static JPanel createRacePanel() {
        JPanel trackPanel = new JPanel();
        trackPanel.setLayout(new BoxLayout(trackPanel, BoxLayout.Y_AXIS));
        trackPanel.setBackground(new Color(0x326b8c));
        trackPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the track panel

        for (Track track : tracks) {
            track.showHorse(); // Show the horse component
            JPanel trackWithSettings = new JPanel(new BorderLayout());
            trackWithSettings.setBackground(new Color(0x326b8c));

            JButton statsButton = new JButton("Stats & Odds");
            statsButton.setPreferredSize(statsButton.getPreferredSize());
            statsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame statsFrame = new JFrame("Horse Stats");
                    statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    statsFrame.setSize(400, 200);
                    statsFrame.setResizable(false);

                    statsFrame.add(new HorseStats(track.getHorseComponent()));

                    statsFrame.setVisible(true);
                }
            });


            JButton settingsButton = new JButton(new ImageIcon("Part2/images/settings.png"));
            settingsButton.setPreferredSize(new Dimension(100, 100));
            settingsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame settingsFrame = new JFrame("Track Settings");
                    settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    settingsFrame.setSize(600, 400);
                    settingsFrame.setResizable(false);

                    settingsFrame.add(new TrackSettingsPanel(track));

                    settingsFrame.setVisible(true);
                }
            });
            
            trackWithSettings.add(statsButton, BorderLayout.EAST);
            trackWithSettings.add(track, BorderLayout.CENTER);
            trackWithSettings.add(settingsButton, BorderLayout.WEST);

            trackPanel.add(trackWithSettings);
        }

        return trackPanel;
    }

    // Update track panel
    private static void updateTrackPanel() {
        mainPanel.remove(trackPanel);
        trackPanel = createRacePanel();
        mainPanel.add(trackPanel, BorderLayout.WEST);
        frame.revalidate();
        frame.repaint();
    }

    private static void updateBettingsPanel() // Update betting panel
    {
        frame.remove(bettingPanel);
        bettingPanel = new BettingPanel(numLanes, tracks);
        frame.add(bettingPanel, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
    }

    // Receive track settings
    public static void receiveTrackSettings(TrackSettingsData data) {
        Color trackColor = data.getTrackColor();
        int trackIndex = data.getId();
        String path = data.getHorsePath();
        String breed = data.getBreed();
        String name = data.getName();

        // Update the track color
        for (Track track : tracks) {
            track.setColor(trackColor);;
        }

        tracks.get(trackIndex).getHorseComponent().setImage(path);
        tracks.get(trackIndex).getHorseComponent().setBreed(breed);
        tracks.get(trackIndex).getHorseComponent().getHorse().setName(name);


        updateTrackPanel(); // Update track panel
    }

    // Method to check if all horses have fallen
    private static boolean allHorsesHaveFallen() {
        for (Track track : tracks) {
            if (!track.getHorseComponent().hasFallen()) {
                return false; // If any horse hasn't fallen, return false
            }
        }

        return true; // All horses have fallen
    }
}
