package Part2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BettingPanel extends JPanel {
    public static double money = 1000;
    public static int wins = 0;
    public static int losses = 0;
    public static int betTrack;
    public static double currentBetAmount;
    public static double odds;
    public static JTextField betInput;

    public static JLabel moneyLabel = new JLabel("Money: $" + money);
    public static JLabel currentBetLabel = new JLabel("Current Bet: $" + currentBetAmount);
    public static JLabel winsLabel = new JLabel("Wins: " + wins);
    public static JLabel lossesLabel = new JLabel("Losses: " + losses);

    public BettingPanel(int numTracks, ArrayList<Track> tracks) {
        setLayout(new GridBagLayout()); 

        // Create GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 15, 10, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Span three columns
        gbc.anchor = GridBagConstraints.CENTER;

        // Add betting label
        JLabel bettingLabel = new JLabel("Place your bets here!");
        add(bettingLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        JPanel selectTrack = new JPanel(new GridLayout(3, 2, 10, 0));
        JLabel trackLabel = new JLabel("Select a track: ");
        JLabel trackNameLabel = new JLabel("Track: None Selected");
        JLabel trackOddsLabel = new JLabel("Odds: None Selected");
        JLabel horseName = new JLabel("Horse: None Selected");
        JLabel horseConfidence = new JLabel("Confidence: None Selected");

        String[] trackSelector = new String[numTracks];
        for(int i = 0; i < numTracks; i++) {
            trackSelector[i] = String.valueOf(i + 1);
        }

        JComboBox<String> trackDropdown = new JComboBox<>(trackSelector);
        // Add an action listener to the track dropdown
        trackDropdown.addActionListener(e -> {
            // Get the selected track name
            String selectedTrackName = (String) trackDropdown.getSelectedItem();

            // Assuming 'tracks' is an ArrayList containing Track objects
            // Find the selected track in the 'tracks' list
            for (Track track : tracks) {
                if (track.getTrackId() == (Integer.parseInt(selectedTrackName)-1)) {
                    // Display track stats
                    System.out.println("Track: " + track.getTrackId());
                    betTrack = track.getTrackId();
                    trackNameLabel.setText("Selected Track: " + (betTrack+1));
                    HorseComponent horse = track.getHorseComponent();
                    odds = OddsCalculator.calculateOdds(horse.getHorse().getConfidence(), horse.getWins(), horse.getLosses());
                    System.out.println("Odds: " + odds);
                    trackOddsLabel.setText("Odds: " + odds);
                    horseName.setText("Horse: " + horse.getHorse().getName());
                    horseConfidence.setText("Confidence: " + horse.getHorse().getConfidence());
                    // Display other stats as needed
                    break; // Break the loop once the track is found
                }
            }
        });
        selectTrack.add(trackLabel);
        selectTrack.add(trackDropdown);
        selectTrack.add(trackNameLabel);
        selectTrack.add(trackOddsLabel);
        selectTrack.add(horseName);
        selectTrack.add(horseConfidence);
        add(selectTrack, gbc);

        // Incrementing row
        gbc.gridy++;

        // Add input box for bets
        JPanel betPanel = new JPanel();
        JLabel betLabel = new JLabel("Enter your bet Amount: ");
        betInput = new JTextField(10);
        JPanel buttonsJPanel = new JPanel(new GridLayout(2, 1));
        JButton betButton = new JButton("Place Bet");
        JButton cancelButton = new JButton("Cancel Bet");
        cancelButton.setBackground(Color.RED);  
        cancelButton.setForeground(Color.WHITE);    

        buttonsJPanel.add(betButton);
        buttonsJPanel.add(cancelButton);
        betPanel.add(betLabel);
        betPanel.add(betInput);
        betPanel.add(buttonsJPanel);
        gbc.gridwidth = 2; // Span two columns
        add(betPanel, gbc);

        
        gbc.gridx = 2;
        gbc.gridy = 1;

        // Add odds information
        JPanel statsPanel = new JPanel(new GridLayout(4, 2, 5, 10));
        moneyLabel = new JLabel("Money: $" + money);
        currentBetLabel = new JLabel("Current Bet: $" + currentBetAmount);
        JLabel estimatedWinningsLabel = new JLabel("Estimated Winnings: $0.00");
        winsLabel = new JLabel("Wins: " + wins);
        lossesLabel = new JLabel("Losses: " + losses);
        statsPanel.add(moneyLabel);
        statsPanel.add(currentBetLabel);
        statsPanel.add(estimatedWinningsLabel);
        statsPanel.add(winsLabel);
        statsPanel.add(lossesLabel);
        add(statsPanel, gbc);


        // Add action listener to the bet button
        betButton.addActionListener(e -> {
            // Get the entered bet amount as a string
            String betAmountStr = betInput.getText();

            try {
                // Parse the entered bet amount to a double
                double betAmount = Double.parseDouble(betAmountStr);

                // Check if the bet amount is valid
                if (betAmount >= 1 && betAmount <= money) {
                    // Place the bet
                    // Your code to place the bet goes here
                    System.out.println("Bet placed: " + betAmount);
                    currentBetAmount = betAmount;

                    money -= betAmount; 
                    money = Math.round(money * 100.0) / 100.0; // Round to 2 decimal places

                    currentBetLabel.setText("Current Bet: $" + currentBetAmount);

                    estimatedWinningsLabel.setText("Estimated Winnings: $" + Math.round((odds * betAmount)*100.0)/100.0);



                } else {
                    if(betAmount < 1){
                        // Display an error message if the bet amount is less than 1
                        JOptionPane.showMessageDialog(this, "Invalid bet amount. Bet must be at least 1.");
                    }
                    else{
                        // Display an error message if the bet amount is greater than the available money
                        JOptionPane.showMessageDialog(this, "Invalid bet amount. Bet must be less than or equal to your available money. \nYou have $" + money + " available.");
                    }
                }
            } catch (NumberFormatException ex) {
                // Display an error message if the entered bet amount is not a valid number
                JOptionPane.showMessageDialog(this, "Invalid bet amount. Please enter a valid number.");
            }

            // Update the money label
            moneyLabel.setText("Money: $" + money);
        });
        cancelButton.addActionListener(e -> {
            // Reset the current bet amount
            money += currentBetAmount;
            moneyLabel.setText("Money: $" + money);
            currentBetAmount = 0;
            currentBetLabel.setText("Current Bet: $" + currentBetAmount);
            betInput.setText("");

        });
    }

    public static void recieveWinningTrack(RaceManager raceManager) {
        // Get the winning track
        boolean win = false;
        Track winningTrack = raceManager.getWinningTrack();

        if(winningTrack.getTrackId() == betTrack) {
            win = true;
            calculateWinnings(odds, currentBetAmount, win);
        } else {
            win = false;
            calculateWinnings(odds, currentBetAmount, win);
        }
    }

    public static void calculateWinnings(double odds, double betAmount, boolean win) {
        if (win) {
            // Calculate the winnings
            double winnings = odds * betAmount;

            // Update the money
            money += winnings;
            money = Math.round(money * 100.0) / 100.0;
            winnings = Math.round(winnings * 100.0) / 100.0;
            moneyLabel.setText("Money: $" + money);


            // Increment the wins
            wins++;
            winsLabel.setText("Wins: " + wins);

            // Display a message with the winnings
            JOptionPane.showMessageDialog(null, "Congratulations! You won $" + winnings + "!");
            win = false;
        } else {
            // Increment the losses
            losses++;
            lossesLabel.setText("Losses: " + losses);

            // Display a message for a loss
            JOptionPane.showMessageDialog(null, "Sorry, you lost the bet.");
        }

        currentBetAmount = 0;
        currentBetLabel.setText("Current Bet: $" + currentBetAmount);
        betInput.setText("");
    }

    
}

