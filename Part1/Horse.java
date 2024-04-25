package Part1;

/**
 * Write a description of class Horse here.
 * 
 * @author Gabriel Kanjama
 * @version 3/04/2024
 */
public class Horse
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private int distanceTravelled;
    private boolean hasFallen;
    private double confidence;
    
    
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.name = horseName;
        this.symbol = horseSymbol;
        this.confidence = horseConfidence;
        this.distanceTravelled = 0;
        this.hasFallen = false;
    }
    
}
