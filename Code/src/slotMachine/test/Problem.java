package slotMachine.test;

import slotMachine.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shapes.Canvas;

import java.util.Map;

/**
 * The test class Problem.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class Problem {
    
    private SlotMachineContest slmch;
    
    @Test
    public void simulate() throws InterruptedException {
        slmch = new SlotMachineContest(3);
        slmch.simulate(3);
        slmch.solve(3);
        slmch = new SlotMachineContest(5);
        slmch.simulate(5);
        slmch.solve(5);
        slmch = new SlotMachineContest(10);
        slmch.simulate(10);
        slmch.solve(10);
        slmch = new SlotMachineContest(4);
        slmch.simulate(4);
        slmch.solve(4);
    }
}