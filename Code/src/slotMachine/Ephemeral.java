package slotMachine;


/**
 * Write a description of class Ephemeral here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ephemeral extends Symbol implements Updatable {
    
    public Ephemeral(String color) {
        super(color);
    }
    
    public Ephemeral(String color, int pos) {
        super(color, pos);
    }    
    
    public void update() {
        symbolShape.decrement();
    }
}