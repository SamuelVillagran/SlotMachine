package slotMachine;

/**
 * Write a description of class Rotation here.
 * This custom symbol moves up and down when selected.
 * 
 * @version 1.0
 */
public class Rotation extends Symbol {

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

    @Override
    public void changePosition(int newPosX, int newPosY) {
        this.baseX = newPosX;
        this.baseY = newPosY;
        super.changePosition(newPosX, newPosY);
    }

    /**
     * Triggers the rotation effect.
     */
    public void rotation() {
        rotating();
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
    @Override
    protected void efect() {
        rotating();
    }
}