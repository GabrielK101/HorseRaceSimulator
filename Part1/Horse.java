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

    public double getConfidence()
    {
        return this.confidence;
    }
    
    public int getDistanceTravelled()
    {
        return this.distanceTravelled;        
    }
    
    public String getName()
    {
        return this.name;        
    }
    
    public char getSymbol()
    {
        return this.symbol;        
    }

    public boolean hasFallen()
    {
        return this.hasFallen;        
    }


    public void setConfidence(double newConfidence)
    {
        this.confidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;        
    }

    public void setName(String newName)
    {
        this.name = newName;
    }

    //Other methods of class Horse
    public void fall()
    {
        this.hasFallen = true;      
    }

    public void standUp()
    {
        this.hasFallen = false;        
    }

    public void goBackToStart()
    {
        this.distanceTravelled = 0; 
        this.hasFallen = false;       
    }

    public void moveForward()
    {
        this.distanceTravelled += 1;
    }
    
}
