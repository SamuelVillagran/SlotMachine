package slotMachine;

/**
 * Write a description of class Rotation here.
 * This custom symbol moves up and down when selected.
 * 
 * @version 1.0
 */
<<<<<<< HEAD
public class Rotation extends Symbol implements Updatable {
=======
public class Rotation extends Symbol implements Updatable{
>>>>>>> 3bd016024d4542030625cfdb1b4b778d4173d575

    private int baseX;
    private int baseY;
    private boolean isUp;

    /**
     * Constructor for objects of class Rotation
     * @param color The color of the rotation symbol.
     */
    public Rotation(String color) {
        super(color);
        this.isUp = false;
    }

    public void changePosition(int newPosX, int newPosY) {
        this.baseX = newPosX;
        this.baseY = newPosY;
        super.changePosition(newPosX, newPosY);
    }

    /**
     * Changes the position of the symbol moving it up and down.
     */
    public void rotating() {
        if (isUp) {
            super.changePosition(baseX, baseY);
            this.isUp = false;
        } else {
            super.changePosition(baseX, baseY - 20);
            this.isUp = true;
        }
    }

    /**
     * Applies the specific effect of the rotation symbol.
     */
    public void update() {
        rotating();
    }
}