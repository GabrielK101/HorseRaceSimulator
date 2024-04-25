package Part2;

/**
 * Write a description of class Horse here.
 * 
 * @author Gabriel Kanjama
 * @version 3/04/2024
 */
public class Horse
{
    //Fields of class Horse
    String name;
    char symbol;
    int distanceTravelled;
    boolean hasFallen;
    double confidence;
    
      
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
    
    
    
    //Other methods of class Horse
    public void fall()
    {
        this.hasFallen = true;
        confidence -= 0.1;
        if(confidence <= 0){
            confidence = 0.09;
        }      
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
    
    public void goBackToStart()
    {
        this.distanceTravelled = 0;        
    }
    
    public boolean hasFallen()
    {
        return this.hasFallen;        
    }

    public void moveForward(double distance)
    {
        this.distanceTravelled += (25*distance);
    }

    public void setConfidence(double newConfidence)
    {
        this.confidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;        
    }
    
    public  void setHasFallen(boolean fallen)
    {
        this.hasFallen = fallen;
    }

    public void setName(String newName)
    {
        this.name = newName;
    }
}

