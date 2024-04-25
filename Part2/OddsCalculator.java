package Part2;

public class OddsCalculator {
    public static double calculateOdds(double confidence, int wins, int losses) {
        int totalRaces = wins + losses;
        double winRate;
    
        // Calculate win rate with a default value of 0.5 if there are no races
        if (totalRaces == 0) {
            winRate = 1; // Default win rate if no races
        } else {
            winRate = (double) (wins + 1) / totalRaces; // Calculating win rate with a pseudocount of 1
        }
    
        // Combine confidence and win rate, and calculate odds
        double combinedScore = confidence * (0.5 + winRate);
        double odds = 1.0 / combinedScore; // Inverse of the combined score represents the odds
        
        return roundThreeDecimals(odds);
    }

    public static double roundThreeDecimals(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}
