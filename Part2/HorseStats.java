package Part2;

import javax.swing.*;
import java.awt.*;

public class HorseStats extends JPanel {
    public HorseStats(HorseComponent horse) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 10, 0);

        JLabel title = new JLabel("Stats");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // Span two columns
        gbc.anchor = GridBagConstraints.CENTER; // Align to the left
        add(title, gbc);

        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JLabel name = new JLabel("Name: " + horse.getHorse().getName());
        JLabel odds = new JLabel("Odds: " + OddsCalculator.calculateOdds(horse.getHorse().getConfidence(), horse.getWins(), horse.getLosses()));
        JLabel confidenceLabel = new JLabel("Confidence: " + horse.getConfidence());
        JLabel winsLabel = new JLabel("Wins: " + horse.getWins()); // Initialize wins label
        JLabel lossesLabel = new JLabel("Losses: " + horse.getLosses());

        statsPanel.add(name);
        statsPanel.add(odds);
        statsPanel.add(confidenceLabel);
        statsPanel.add(winsLabel);
        statsPanel.add(lossesLabel);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Occupy one column
        gbc.fill = GridBagConstraints.BOTH;
        add(statsPanel, gbc);

        setVisible(true);
    }
}

