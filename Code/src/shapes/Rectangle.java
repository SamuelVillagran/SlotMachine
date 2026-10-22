package shapes;
import java.awt.*;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @version 1.0  (15 July 2000)()
 */
public class Rectangle extends Polygon2D {

    public static final int EDGES = 4;
    
    
    /**
     * Create a new rectangle at default position with default color.
     */
    public Rectangle(){
        super(70, 15, "green");
        height = 30;
        width = 40;
    }
    
    public Rectangle(int xPosition, int yPosition, int height, int width, String color) {
        super(xPosition, yPosition, color);
        this.height = height;
        this.width = width;
    }
    
    /*
     * Draw rectangle
     */
    @Override
    public void draw() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, 
                                       width, height));
            canvas.wait(10);
        }
    }
    
}

