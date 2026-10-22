package slotMachine.test;

import slotMachine.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

/**
 * The test class SlotMachineTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineC1Test {
    
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
    public void shouldCreateSlotMachine() {
        assertTrue(wheels != null); // Comprueba que existe
        sltmchn.addWheel(1);
        Wheel wheel1 = wheels.get(1);
        Map<Integer, Symbol> symbolsCreated = wheel1.getSymbols();
        assertTrue(symbolsCreated != null); // Se deben crear tambien la existencia de los simbolos
    }
    
    @Test
    public void shouldAddWheel() {
        sltmchn.addWheel(1);
        sltmchn.addWheel(2);
        Wheel firstWheel = wheels.get(1);
        Wheel secondWheel = wheels.get(2);
        int xPosWheelFirst = firstWheel.getXPosition(), xPosWheelSecond = secondWheel.getXPosition(); // Moves 25*2
        assertEquals(20, xPosWheelFirst); // Se verifica que se ubica en la posicion correcta
        assertEquals(70, xPosWheelSecond);
    }
    
    @Test
    public void shouldDelWheel() {
        sltmchn.addWheel(1);
        Wheel wheelWillBeDeleted = wheels.get(0);
        sltmchn.delWheel(1);
        assertFalse(wheels.containsValue(wheelWillBeDeleted)); // Se verifica su ausencia en la lista
        assertEquals(0, wheels.size());
    }
    
    @Test
    public void shouldGiveSymbols() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.addSymbol(1, "magenta");
        sltmchn.addSymbol(2, "red");
        sltmchn.addSymbol(3, "yellow");
        sltmchn.addSymbol(4, "blue");
        sltmchn.addSymbol(5, "green");
        String[] proof = sltmchn.symbols();
        String[] result = new String[]{"magenta", "red", "yellow", "blue", "green"};
        assertArrayEquals(result, proof);
    }
    
    @Test
    public void shouldGiveConfiguration() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.addSymbol(1, "magenta");
        sltmchn.addSymbol(2, "red");
        sltmchn.addSymbol(3, "yellow");
        sltmchn.addSymbol(4, "blue");
        sltmchn.addSymbol(5, "green");
        String[] proof = sltmchn.configuration();
        String[] result = new String[]{"magenta","red", "yellow","blue", "green"};
        for (int i = 0; i < result.length;i++) {
            assertEquals(proof[i], result[i]);
        }
    }
    
    @Test
    public void shouldGiveNumberDistingSymbols() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.addSymbol(1, "magenta");
        sltmchn.addSymbol(2, "red");
        sltmchn.addSymbol(3, "yellow");
        sltmchn.addSymbol(4, "blue");
        sltmchn.addSymbol(5, "green");
        int numDistinctSymbols = sltmchn.distinctSymbols();
        assertEquals(5, numDistinctSymbols);
    }
    
    @Test
    public void shouldGiveOtherNumberDistingSymbols() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "red");
            sltmchn.addSymbol(i, "yellow");
        }
        sltmchn.addSymbol(1, "write");
        sltmchn.addSymbol(2, "black");
        sltmchn.addSymbol(3, "blue");
        sltmchn.addSymbol(4, "green"); // Solamente colocamos 4 simbolos distintos en distintas ruedas
        
        int numDistinctSymbols = sltmchn.distinctSymbols();
        assertEquals(4, numDistinctSymbols);
    }
    
    @Test
    public void shouldBeJackpot() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "red");
            sltmchn.addSymbol(i, "yellow");
        }
        boolean isJackpot = sltmchn.isJackpot();
        assertTrue(isJackpot);
    }
    
    @Test
    public void shouldntBeJackpot() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "red");
            sltmchn.addSymbol(i, "yellow");
        }
        Wheel secondWheel = wheels.get(1);
        secondWheel.setSelectedSymbol(new Symbol("blue"));
        boolean isJackpot = sltmchn.isJackpot();
        assertFalse(isJackpot);
    }
    
    @Test
    public void shouldExitTheProgram() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.exit();
        assertTrue(wheels.isEmpty());
    }
    
    @Test
    public void shouldFailWhenAddingSymbolToNonexistentWheel() { //Prueba supervisada y ayudada a hacer con Gemini Pro 3.1 IA
        sltmchn.addWheel(1);
        sltmchn.addSymbol(5, "red"); // Se intenta añadir a una rueda que no existe físicamente (si no hay auto-ajuste de índice)
        
        // En tu implementación, proofInvariant con isAdding = false lanzará false
        assertFalse(sltmchn.ok(), "No debe permitir agregar un símbolo a una rueda inexistente");
    }
    
    @Test
    public void shouldPlaceSymbolCorrectly() {//Prueba supervisada y ayudada a hacer con Gemini Pro 3.1 IA
        sltmchn.addWheel(1);
        sltmchn.addSymbol(1, "red");
        sltmchn.addSymbol(1, "blue");
        sltmchn.addSymbol(1, "green");
        
        // Acción: Forzamos a que el símbolo visible sea el verde
        sltmchn.placeSymbol(1, "green");
        
        // Verificación: Revisamos que el puntero selectedSymbol haya cambiado correctamente
        String[] currentConfig = sltmchn.configuration();
        assertEquals("green", currentConfig[0], "El símbolo al frente de la rueda 1 debe ser 'green'");
        assertTrue(sltmchn.ok(), "La operación de ubicar el símbolo debió marcar ok() como true");
    }

    @Test
    public void shouldNotPlaceSymbolWhenWheelIsLocked() {//Prueba supervisada y ayudada a hacer con Gemini Pro 3.1 IA
        sltmchn.addWheel(1);
        sltmchn.addSymbol(1, "magenta");
        sltmchn.addSymbol(1, "yellow");
        
        // Forzamos a que inicie en magenta y luego bloqueamos la rueda
        sltmchn.placeSymbol(1, "magenta");
        sltmchn.lock(1);
        
        // Acción: Intentamos cambiarlo a amarillo mientras está bloqueada
        sltmchn.placeSymbol(1, "yellow");
        
        // Verificación: La rueda debió ignorar el cambio por el condicional !isLocked
        String[] currentConfig = sltmchn.configuration();
        assertEquals("magenta", currentConfig[0], "El símbolo no debió cambiar porque la rueda está bloqueada");
    }
    
    @AfterEach
    void tearDown() {
        wheels.clear();
    }
}
