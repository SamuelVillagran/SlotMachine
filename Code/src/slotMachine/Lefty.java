package slotMachine;
import java.util.Map;
import java.util.TreeMap;


/**
 * Write a description of class Lefty here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lefty extends Wheel implements Updatable {
    private Wheel wheelToCopy;
    
    public Lefty(Wheel wheelToCopy) {
        if (this.positionWheel != 1) this.wheelToCopy = wheelToCopy;
    }
    
    public void update() {
        copyWheel();
        boolean isVisible = this.wheelShape.isVisible();
        if (isVisible) {
            this.wheelShape.frameFlickering();
        }
    }
    
    private void copyWheel() {
        int positionWheelCopy = wheelToCopy.getPositionWheel(), selectedSymbolIntToCopy = wheelToCopy.getSelectedSymbolInteger();
        boolean isLockedToCopy = wheelToCopy.isLocked();
        Symbol selectedSymbolToCopy = wheelToCopy.getSelectedSymbol();
        Map<Integer,Symbol> symbolsToCopy = wheelToCopy.getSymbols();
        if (this.positionWheel != 1) {
            this.selectedSymbolInteger = selectedSymbolIntToCopy;
            this.isLocked = isLockedToCopy;
            this.selectedSymbol = selectedSymbolToCopy;
            this.symbols = new TreeMap<>(symbolsToCopy);
        } else if (this.positionWheel == 1) {
            throw new SlotMachineException(SlotMachineException.CANT_LEFT_COPY_WHEEL);
        }
    }
}