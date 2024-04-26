package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrackSettingsPanel extends JPanel {
    private JButton prevButton;
    private JButton nextButton;

    private Track track;
    private HorseComponent horse;
    private String[] horses = new String[]{"horse", "horsebrown", "horsegold", "horsegray", "horsewhite"};
    private JPanel trackPanel;
    private int currentHorseIndex = 0;
    private String newhorseName;

    public TrackSettingsPanel(Track trackEdit) {
        this.track = new Track(200, 100, trackEdit.getTrackId());
        this.horse = track.getHorseComponent();
        this.horse.setSize(100);


        setLayout(new BorderLayout(10, 10)); // Set horizontal and vertical gaps
        setBackground(new Color(0x009957));

        // Create a panel to hold both buttons
        JPanel topButtonPanel = new JPanel(new GridLayout(3, 1));

        // "Save Changes" button
        JButton saveChangesButton = new JButton("Save Changes");
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new TrackSettingsData instance and set the color
                TrackSettingsData data = new TrackSettingsData();
                String breed = horses[currentHorseIndex];
                data.setTrackColor(track.trackid, track.getBackground(), breed, newhorseName);

                // Send the data back to RaceGUI
                startRaceGUI.receiveTrackSettings(data);

                // Dispose the frame
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(TrackSettingsPanel.this);
                frame.dispose();
            }
        });
        topButtonPanel.add(saveChangesButton);

        JPanel horsePanel = new JPanel();
        horsePanel.add(new JLabel("Select Horse Breed:"));


        // Initialize navigation buttons
        prevButton = new JButton("<");
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreviousHorse();
            }
        });

        nextButton = new JButton(">");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextHorse();
            }
        });
        horsePanel.add(prevButton);
        horsePanel.add(nextButton);
        
        topButtonPanel.add(horsePanel);


        JPanel namePanel = new JPanel();
        JTextField horseName = new JTextField(trackEdit.getHorseComponent().getHorse().getName(), 5);
        JButton saveHorseName = new JButton("Change Name");
        saveHorseName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newhorseName = horseName.getText();
            }
        });
        JLabel horseLabel = new JLabel("Horse Name: ");
        namePanel.add(horseLabel);
        namePanel.add(horseName);
        namePanel.add(saveHorseName);
        topButtonPanel.add(namePanel);

        // Add the combined button panel to the main panel
        add(topButtonPanel, BorderLayout.NORTH);
        

        // Create a panel to hold the Track
        trackPanel = new JPanel(new BorderLayout());
        trackPanel.setBackground(new Color(0x009957));
        trackPanel.add(horse, BorderLayout.NORTH);
        trackPanel.add(track, BorderLayout.SOUTH);

        // Add the trackPanel to the center position
        add(trackPanel, BorderLayout.WEST);

        // Create a panel for color buttons
        JPanel colorButtonPanel = new JPanel();
        JLabel colorLabel = new JLabel("Edit Track Surface: ");
        colorButtonPanel.add(colorLabel);

        // Add color buttons
        JButton redButton = new JButton("Default");
        redButton.addActionListener(new ColorButtonListener(new Color(0xb33c34)));
        colorButtonPanel.add(redButton);

        JButton dirtButton = new JButton("Dirt");
        dirtButton.addActionListener(new ColorButtonListener(new Color(0x664321)));
        colorButtonPanel.add(dirtButton);

        JButton grassButton = new JButton("Grass");
        grassButton.addActionListener(new ColorButtonListener(new Color(0x2e7d32)));
        colorButtonPanel.add(grassButton);

        JButton iceButton = new JButton("Ice");
        iceButton.addActionListener(new ColorButtonListener(new Color(0xb9e9eb)));
        colorButtonPanel.add(iceButton);

        JButton button = new JButton("Custom Color");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Color color = JColorChooser.showDialog(null, "Choose a color", track.getBackground());
                track.setColor(color);
                track.repaint(); // Repaint the track to reflect the color change
                trackPanel.revalidate(); // Revalidate the panel hierarchy to reflect changes                
            }
        });
        colorButtonPanel.add(button);

        // Add the colorButtonPanel to the SOUTH position
        add(colorButtonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void showNextHorse() {
        currentHorseIndex = (currentHorseIndex + 1) % horses.length;
        refreshTrackPanel();
    }

    private void showPreviousHorse() {
        currentHorseIndex = (currentHorseIndex - 1 + horses.length) % horses.length;
        refreshTrackPanel();
    }

    private void refreshTrackPanel() {
        trackPanel.remove(horse);
        trackPanel.remove(track);
        horse.setImage("Part2/images/" + horses[currentHorseIndex] + ".png");
        trackPanel.add(horse, BorderLayout.NORTH);
        trackPanel.add(track, BorderLayout.SOUTH);
        trackPanel.revalidate();
        trackPanel.repaint();
    }
    
    private class ColorButtonListener implements ActionListener {
        private Color color;
    
        public ColorButtonListener(Color color) {
            this.color = color;
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            track.setColor(color);
            refreshTrackPanel();
            track.repaint(); // Repaint the track to reflect the color change
            trackPanel.revalidate(); // Revalidate the panel hierarchy to reflect changes
        }
    }
    
}