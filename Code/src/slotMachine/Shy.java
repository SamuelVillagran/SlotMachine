package slotMachine;


/**
 * Write a description of class Shy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shy extends Symbol implements Updatable {
    
    public Shy(String color) {
        super(color);
    }
    
    public Shy(String color, int pos) {
        super(color, pos);
    }
    
    public void update() {
        if (isSelected) {
            if (!isVisible()) makeVisible();
            if (isVisible()) makeInvisible();
        }
    }
    
}