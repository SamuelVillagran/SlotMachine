package src.slotMachine;


/**
 * Write a description of class SlotMachineException here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SlotMachineException extends RuntimeException {
    public final static String CANT_SWAP_REBEL = "A rebel wheel cannot be swap.";
    public final static String CANT_DELETE_REBEL = "A rebel wheel cannot be deleted.";
    public final static String CANT_LOCK_REBEL = "A rebel wheel cannot be locked.";
    public final static String CANT_LEFT_COPY_WHEEL = "A left wheel can't copy some wheel because it's the first wheel of slotmachine.";
    
    public SlotMachineException(String message) {
        super(message);
    }
}
