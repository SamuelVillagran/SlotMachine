package slotMachine.test;

import slotMachine.*;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TestWheel.
 *
 * @author  Steveen-Gualdron
 * @version 0.1
 */
public class WheelTest
{
    private Wheel wheel;
    @BeforeEach
    void setUp() {
        wheel = new Wheel();
    }

    @Test
    public void changeColorAndPositionTest(){
        wheel.changeColor("black");
        wheel.changeColor("blue");
        wheel.changeColor("white");
        wheel.changePosition(120, 40);
        assertEquals("white", wheel.getColor(), "The color of the wheel should be 'white'");
        assertEquals(120, wheel.getXPosition(), "The X position should be 120");
        assertEquals(40, wheel.getYPosition(), "The Y position should be 40");
    
    }   
    @Test
    public void addSymbols(){
        Symbol triangle1 = new Symbol("black");
        Symbol triangle2 = new Symbol("blue");
        Symbol triangle3 = new Symbol("white");
        wheel.addSymbol(triangle1);
        wheel.addSymbol(triangle2);
        wheel.addSymbol(triangle3);
        assertEquals(triangle1, wheel.selectedSymbol());
        wheel.delSymbol(triangle3);
        
    }
    
    @Test
    public void deleteSymbols() {
        Symbol triangle1 = new Symbol("black");
        Symbol triangle2 = new Symbol("blue");
        Symbol triangle3 = new Symbol("white");
        wheel.addSymbol(triangle1);
        wheel.addSymbol(triangle2);
        wheel.addSymbol(triangle3);
        wheel.delSymbol(triangle1);
        wheel.delSymbol(triangle2);
        assertEquals(triangle3, wheel.selectedSymbol());
    }
}
