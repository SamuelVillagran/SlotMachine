package shapes;
/**
 * Class Figure, each figure of shapes is Figure
 * 
 * @author Gualdron - Villagran
 * @version 1.0
 */
public abstract class Figure {
    
    protected String color;
    protected boolean isVisible;
    protected int xPosition;
    protected int yPosition;
    
    protected Figure(int x, int y, String color) {
        this.xPosition = x;
        this.yPosition = y;
        this.color = color;
        isVisible = false;
    }
    
    /**
     * Make this shape visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }
    
    /**
     * Make this shape invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }
    
    /**
     * Move the shape a few pixels to the right.
     */
    public void moveRigth(int distance) {
        erase();
        xPosition += distance;
        draw();
    }
    
    /**
     * Move the shape a few pixels to the left.
     */
    public void moveLeft() {
        moveHorizontal(-20);
    }
    
    /**
     * Move the shape a few pixels up.
     */
    public void moveUp() {
        moveVertical(-20);
    }
    
    /**
     * Move the shape a few pixels down.
     */
    public void moveDown() {
        moveVertical(20);
    }
    
    /**
     * Move the shape vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }
    
    /**
     * Move the shape horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }
    
    /**
     * Slowly move the shape horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance) {
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }
    
    /**
     * Slowly move the shape vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance) {
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }
    
    /**
     * Change the shape's color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "black", "white", "magenta", "orange", "pink", "cyan", "gray", "lightGray",
     * "darkGray", "brown" and "maroon".
     */
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }
    
    /**
     * Get position x of shape
     * @return Position x of shape
     */    
    public int getXPosition(){
        return xPosition;
    }
    
    
    /**
     * Get position y of shape
     * @return Position y of shape
     */    
    public int getYPosition(){
        return yPosition;
    }
    
    /**
     * Set new x and y position
     * @param x x is the new xPosition of figure.
     * @param y y is the new yPosition of figure.
     */
    public void setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }
    
    /**
     * Erasing shape on canvas
     * 
     */  
    public void erase() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Get the figure color
     */
    public String getColor() {
        return color;
    }
    
    /**
     * Give the attribute visible
     */
    public boolean isVisible() {
        return isVisible;
    }
    
    public void frameFlickering() {
        makeInvisible();
        makeVisible();
    }
    
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    /**
     * Draw the shape on canvas
     */
    protected abstract void draw();
    
}