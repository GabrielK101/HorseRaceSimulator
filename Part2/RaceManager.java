package Part2;

public class RaceManager {
    private Track winningTrack;

    public Track getWinningTrack() {
        return winningTrack;
    }

    // Method to be called when the race ends
    public void raceEnded(Track winningTrack) {
        this.winningTrack = winningTrack;        
    }
}

