package slotMachine.test;

import slotMachine.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * The test class SlotMachineC4Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineC4Test {
    
    private SlotMachine machine;
    private Map<Integer, Wheel> wheels;
    private Wheel wheel;
    private int numWheels;
    
    @BeforeEach
    public void setUp() {
        machine = new SlotMachine();
         wheel = new Rebel();
    }
    
    @Test
    public void shouldCopyAtWheelLeft() {
        machine.addWheel(1);
        machine.addWheel("Lefty",2);
        machine.addSymbol(1,"blue");
        machine.spin(2);
        assertArrayEquals(new String[] {"blue", "blue"}, machine.configuration());
        machine.addSymbol(2,"red");
        try {
            machine.spin(1,3);
            assertArrayEquals(new String[] {"blue", "blue"}, machine.configuration());
            machine.spin();
            assertArrayEquals(new String[] {"blue", "blue"}, machine.configuration());
        }
        catch(Exception e) {};
    }
    
    @Test
    public void shouldStart() {
        Symbol symbol1 = new Symbol("red");
        Symbol symbol2 = new Symbol("blue");
        Symbol symbol3 = new Symbol("black");
        wheel.addSymbol(symbol1);
        wheel.addSymbol(symbol2);
        wheel.addSymbol(symbol3);
        assertArrayEquals(new String[]{"red", "blue", "black"}, wheel.symbols());
    }
    
    @Test
    public void shouldNotBlock() {
        assertThrows(SlotMachineException.class, () -> {
        wheel.lock();
        });
        
        //Verifica que, efectivamente, no se bloqueó
        assertFalse(wheel.isLocked());
    }
    
    @Test
    public void shouldNotExchange() {
        int pos = wheel.getPositionWheel();
        Wheel wheelCange = new Rebel();
        Exception miError = assertThrows(SlotMachineException.class, () -> {
            wheel.swap(wheelCange);
        });
    }
    
    @Test
    public void shouldNotDelete() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("Rebel", 1);
        assertThrows(SlotMachineException.class, () -> {
            machine.delWheel(1);
        });
    }
}