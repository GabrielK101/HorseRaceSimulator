package Part1;

import java.util.Scanner;

public class startRace {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String answer = "y";
        Race race = new Race(10);
        while (answer.equals("y")) {           
            race.startRace();
            System.out.print("Do you want to start another race? (y/n): ");
            answer = scanner.nextLine();
        }
    }
}