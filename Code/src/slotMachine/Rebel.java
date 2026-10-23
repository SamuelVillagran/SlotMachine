package src.slotMachine;


/**
 * Write a description of class Rebel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rebel extends Wheel {
    
    public Rebel() {
        super();    
    }
    
    @Override
    public void lock() {
        this.isLocked = false;
        throw new SlotMachineException(SlotMachineException.CANT_LOCK_REBEL);
    }
    
    @Override
    public void unlock() {
        this.isLocked = false;
    }
    
    @Override
    public void swap(Wheel wheelToSwap) {
        throw new SlotMachineException(SlotMachineException.CANT_SWAP_REBEL);
    }
    
    @Override
    public boolean canDelete() {
        throw new SlotMachineException(SlotMachineException.CANT_DELETE_REBEL);
    }
    
}
