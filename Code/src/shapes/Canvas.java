package shapes;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example. 
 *
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 *
 * @version: 1.6 (shapes)
 */
public class Canvas{
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

    private static Canvas canvasSingleton;
    private static final Map<String, Color> PALETTE = new HashMap<>();
    static {
        PALETTE.put("red",     Color.red);
        PALETTE.put("black",   Color.black);
        PALETTE.put("blue",    Color.blue);
        PALETTE.put("yellow",  Color.yellow);
        PALETTE.put("green",   Color.green);
        PALETTE.put("magenta", Color.magenta);
        PALETTE.put("white",   Color.white);
        PALETTE.put("orange",  Color.orange);
        PALETTE.put("pink",    Color.pink);
        PALETTE.put("cyan",    Color.cyan);
        PALETTE.put("gray",    Color.gray);
        PALETTE.put("lightGray",Color.lightGray);
        PALETTE.put("darkGray",Color.darkGray);
        
        PALETTE.put("#FF0000", Color.decode("#FF0000"));
        PALETTE.put("#8B0000", Color.decode("#8B0000"));
        PALETTE.put("#DC143C", Color.decode("#DC143C"));
        PALETTE.put("#FF6347", Color.decode("#FF6347"));
        PALETTE.put("#FFC0CB", Color.decode("#FFC0CB"));
        PALETTE.put("#FF1493", Color.decode("#FF1493"));
        PALETTE.put("#C71585", Color.decode("#C71585"));
        PALETTE.put("#FF4500", Color.decode("#FF4500"));
        PALETTE.put("#FF8C00", Color.decode("#FF8C00"));
        PALETTE.put("#FFA500", Color.decode("#FFA500"));
        PALETTE.put("#FFD700", Color.decode("#FFD700"));
        PALETTE.put("#FFFF00", Color.decode("#FFFF00"));
        PALETTE.put("#F0E68C", Color.decode("#F0E68C"));
        PALETTE.put("#B8860B", Color.decode("#B8860B"));
        PALETTE.put("#008000", Color.decode("#008000"));
        PALETTE.put("#00FF00", Color.decode("#00FF00"));
        PALETTE.put("#32CD32", Color.decode("#32CD32"));
        PALETTE.put("#228B22", Color.decode("#228B22"));
        PALETTE.put("#8FBC8F", Color.decode("#8FBC8F"));
        PALETTE.put("#00FA9A", Color.decode("#00FA9A"));
        PALETTE.put("#6B8E23", Color.decode("#6B8E23"));
        PALETTE.put("#0000FF", Color.decode("#0000FF"));
        PALETTE.put("#000080", Color.decode("#000080"));
        PALETTE.put("#1E90FF", Color.decode("#1E90FF"));
        PALETTE.put("#4682B4", Color.decode("#4682B4"));
        PALETTE.put("#00BFFF", Color.decode("#00BFFF"));
        PALETTE.put("#00FFFF", Color.decode("#00FFFF"));
        PALETTE.put("#48D1CC", Color.decode("#48D1CC"));
        PALETTE.put("#5F9EA0", Color.decode("#5F9EA0"));
        PALETTE.put("#800080", Color.decode("#800080"));
        PALETTE.put("#4B0082", Color.decode("#4B0082"));
        PALETTE.put("#8A2BE2", Color.decode("#8A2BE2"));
        PALETTE.put("#DA70D6", Color.decode("#DA70D6"));
        PALETTE.put("#EE82EE", Color.decode("#EE82EE"));
        PALETTE.put("#FF00FF", Color.decode("#FF00FF"));
        PALETTE.put("#9370DB", Color.decode("#9370DB"));
        PALETTE.put("#A52A2A", Color.decode("#A52A2A"));
        PALETTE.put("#8B4513", Color.decode("#8B4513"));
        PALETTE.put("#D2691E", Color.decode("#D2691E"));
        PALETTE.put("#F4A460", Color.decode("#F4A460"));
        PALETTE.put("#DEB887", Color.decode("#DEB887"));
        PALETTE.put("#FFDEAD", Color.decode("#FFDEAD"));
        PALETTE.put("#000000", Color.decode("#000000"));
        PALETTE.put("#2F4F4F", Color.decode("#2F4F4F"));
        PALETTE.put("#696969", Color.decode("#696969"));
        PALETTE.put("#808080", Color.decode("#808080"));
        PALETTE.put("#A9A9A9", Color.decode("#A9A9A9"));
        PALETTE.put("#C0C0C0", Color.decode("#C0C0C0"));
        PALETTE.put("#DCDCDC", Color.decode("#DCDCDC"));
        PALETTE.put("#F5F5DC", Color.decode("#F5F5DC"));
    }

    /**
     * Factory method to get the canvas singleton object.
     */
    public static Canvas getCanvas(){
        if(canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 300, 300, 
                                         Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    //  ----- instance part -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List <Object> objects;
    private HashMap <Object,ShapeDescription> shapes;
    
    /**
     * Create a Canvas.
     * @param title  title to appear in Canvas Frame
     * @param width  the desired width for the canvas
     * @param height  the desired height for the canvas
     * @param bgClour  the desired background colour of the canvas
     */
    public Canvas(String title, int width, int height, Color bgColour){
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList <Object>();
        shapes = new HashMap <Object,ShapeDescription>();
    }
    
    
    
    public void setDimension(int width, int height) {
        canvas.setPreferredSize(new Dimension(width, height));
        frame.pack();
        
        Dimension size = canvas.getSize();
        canvasImage = canvas.createImage(size.width, size.height);
        graphic = (Graphics2D)canvasImage.getGraphics();
        graphic.setColor(backgroundColour);
        graphic.fillRect(0, 0, size.width, size.height);
        graphic.setColor(Color.black);
        

    }
    

    /**
     * Set the canvas visibility and brings canvas to the front of screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * @param visible  boolean value representing the desired visibility of
     * the canvas (true or false) 
     */
    public void setVisible(boolean visible){
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background colour
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Draw a given shape onto the canvas.
     * @param  referenceObject  an object to define identity for this shape
     * @param  color            the color of the shape
     * @param  shape            the shape object to be drawn on the canvas
     */
     // Note: this is a slightly backwards way of maintaining the shape
     // objects. It is carefully designed to keep the visible shape interfaces
     // in this project clean and simple for educational purposes.
    public void draw(Object referenceObject, String color, Shape shape){
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }
     
    
    /**
     * Erase a given shape's from the screen.
     * @param  referenceObject  the shape object to be erased 
     */
    public void erase(Object referenceObject){
        objects.remove(referenceObject);   // just in case it was already there
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Set the foreground colour of the Canvas.
     * @param colorString  the new colour for the foreground of the Canvas 
     */
    public void setForegroundColor(String colorString){
        graphic.setColor(PALETTE.getOrDefault(colorString, Color.black));
    }

    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number 
     */
    public void wait(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e){
            // ignoring exception at the moment
        }
    }

    /**
     * Redraw ell shapes currently on the Canvas.
     */
    private void redraw(){
        erase();
        for(Iterator i=objects.iterator(); i.hasNext(); ) {
                       shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }
       
    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase(){
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }


    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel{
        public void paint(Graphics g){
            g.drawImage(canvasImage, 0, 0, null); 
        }
    }
    
    public void close() {
        objects.clear();
        shapes.clear();

        erase();

        canvas.repaint();
        frame.dispose();
        canvasSingleton = null;
    }
    
    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class ShapeDescription{
        private Shape shape;
        private String colorString;

        public ShapeDescription(Shape shape, String color){
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic){
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }
    
    public void resizeAndRefresh(int width, int height) {
        setDimension(width, height);
        redraw();
    }

}