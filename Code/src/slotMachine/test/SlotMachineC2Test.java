package slotMachine.test;

import slotMachine.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import java.util.Map;

/**
 * The test class SlotMachineC2.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineC2Test {
    
    private SlotMachine sltmchn;
    private Map<Integer, Wheel> wheels;
    private int numWheels;
    
    @BeforeEach
    void setUp() {
        sltmchn = new SlotMachine();
        wheels = sltmchn.getWheels();
        numWheels = wheels.size();
    }
    
    @Test
    public void shouldSwapTwoWheels() {
        sltmchn.addWheel(1);
        sltmchn.addWheel(2);
        sltmchn.addWheel(3);
        sltmchn.addWheel(4);
        Wheel firstWheel = wheels.get(1), secondWheel = wheels.get(2), thirdWheel = wheels.get(3), fourthWheel = wheels.get(4);
        sltmchn.swap(4, 2);
        sltmchn.swap(1, 3);
        Wheel proofWheel1 = wheels.get(1), proofWheel2 = wheels.get(2), proofWheel3 = wheels.get(3), proofWheel4 = wheels.get(4);
        assertEquals(proofWheel1, thirdWheel);
        assertEquals(proofWheel2, fourthWheel);
        assertEquals(proofWheel3, firstWheel);
        assertEquals(proofWheel4, secondWheel);
        assertEquals(4, wheels.size());
    }
    
    @Test
    public void shouldLockSpecificWheel() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "blue");
            sltmchn.addSymbol(i, "yellow");
            sltmchn.addSymbol(i, "red");
            sltmchn.addSymbol(i, "green");
            sltmchn.addSymbol(i, "blue");
            sltmchn.addSymbol(i, "white");
            sltmchn.addSymbol(i, "blue");
        }
        Wheel secondWheel = wheels.get(1), fourthWheel = wheels.get(3);
        Symbol proofSecWheel = secondWheel.getSelectedSymbol(), proofFourthWheel =  fourthWheel.getSelectedSymbol();
        sltmchn.lock(2);
        sltmchn.lock(4);
        sltmchn.spin(2);
        sltmchn.spin(4);
        Symbol selectedSymbolSecWheel = secondWheel.getSelectedSymbol(), selectedSymbolFourthWheel =  fourthWheel.getSelectedSymbol();
        assertEquals(proofSecWheel, selectedSymbolSecWheel);
        assertEquals(proofFourthWheel, selectedSymbolFourthWheel);
    }
    
    @Test
    public void shouldUnlockSpecificWheel() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "blue");
            sltmchn.addSymbol(i, "yellow");
            sltmchn.addSymbol(i, "red");
            sltmchn.addSymbol(i, "green");
            sltmchn.addSymbol(i, "blue");
            sltmchn.addSymbol(i, "white");
            sltmchn.addSymbol(i, "blue");
        }
        Wheel secondWheel = wheels.get(2), fourthWheel = wheels.get(4);
        Symbol proofSecWheel = secondWheel.getSelectedSymbol(), proofFourthWheel = fourthWheel.getSelectedSymbol();
    
        sltmchn.lock(2);
        sltmchn.lock(4);
        sltmchn.spin(2);
        sltmchn.spin(4);
        Symbol selectedSymbolSecWheel = secondWheel.getSelectedSymbol(), selectedSymbolFourthWheel = fourthWheel.getSelectedSymbol();
        assertEquals(proofSecWheel, selectedSymbolSecWheel);       // sigue igual porque está locked
        assertEquals(proofFourthWheel, selectedSymbolFourthWheel); // sigue igual porque está locked
    
        sltmchn.unlock(2);
        sltmchn.unlock(4);
        sltmchn.spin(2);
        sltmchn.spin(4);
        selectedSymbolSecWheel = secondWheel.getSelectedSymbol();
        selectedSymbolFourthWheel = fourthWheel.getSelectedSymbol();
        //assertNotEquals(proofSecWheel, selectedSymbolSecWheel);       // cambió porque ya no está locked
        //assertNotEquals(proofFourthWheel, selectedSymbolFourthWheel); // cambió porque ya no está locked
    }
    
    @Test
    public void shouldntDeleteWheelWhenIsLocked() throws InterruptedException{
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.lock(5);
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.delWheel(i);
        }
        assertFalse(sltmchn.ok());
    }
    
    @Test
    public void shouldntSwapWheelWhenIsLocked() throws InterruptedException{
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        Wheel wheel1 = wheels.get(1), wheel2 = wheels.get(2), wheel4 = wheels.get(4), wheel5 = wheels.get(5);
        wheel1.addSymbol("cyan");
        wheel2.addSymbol("yellow");
        wheel4.addSymbol("magenta");
        wheel5.addSymbol("blue");
        sltmchn.lock(2);
        sltmchn.lock(4);
        sltmchn.swap(2, 4);
        assertFalse(sltmchn.ok());
        sltmchn.swap(1, 5);
        assertTrue(sltmchn.ok());
        sltmchn.swap(2, 5);
        assertFalse(sltmchn.ok());
    }
    
    @Test 
    public void shouldntSpinWhenWheelIsLocked() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "blue");
            sltmchn.addSymbol(i, "yellow");
        }
        sltmchn.lock(5);
        sltmchn.spin(); // El último spin debió hacer que isOK sea false
        assertFalse(sltmchn.ok());
        sltmchn.unlock(5);
        sltmchn.spin(); // Ya desbloqueada debería funcionar
        assertTrue(sltmchn.ok());
        sltmchn.lock(1);
        sltmchn.lock(3);
        sltmchn.spin(1); // Solamente deberian de girar la rueda 2, 4, 5 (Cambian de selectedSymbol o el simbolo principal)
        assertFalse(sltmchn.ok());
        sltmchn.spin(2);
        assertTrue(sltmchn.ok());
        sltmchn.spin(3);
        assertFalse(sltmchn.ok());
    }
    
    @Test
    public void shouldSpinToObtainASpecificConfigurationOfSymbols() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "blue");
            sltmchn.addSymbol(i, "yellow");
            sltmchn.addSymbol(i, "red");
            sltmchn.addSymbol(i, "green");
            sltmchn.addSymbol(i, "gray");
            sltmchn.addSymbol(i, "white");
            sltmchn.addSymbol(i, "cyan");
            sltmchn.addSymbol(i, "pink");
        }
        String[] specificConfiguration = {"blue", "cyan", "yellow", "green", "pink"};
        
        // Acción de girar rueda con una especificacion
        sltmchn.spin(specificConfiguration);
        String[] configurationSlotMachine = sltmchn.configuration();
        // Verificación 
        assertArrayEquals(specificConfiguration, configurationSlotMachine, "La configuración resultante no coincide con la esperada");
        assertTrue(sltmchn.ok());
    }
}