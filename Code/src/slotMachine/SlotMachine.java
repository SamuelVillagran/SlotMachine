package slotMachine;
import shapes.Rectangle;
import shapes.Canvas;
import shapes.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import javax.swing.JOptionPane;
import java.util.Map;
import java.util.TreeMap;

/**
 * This is slot machine game, this is a variant of I problem - SlotMachine ICPC competition
 *
 * @author Steveen-Gualdron
 * @version 0.1
 */
public class SlotMachine {
    private boolean isOk;
    private NavigableMap<Integer, Wheel> wheels; //Key represents the position of wheel at the slotmachine
    private boolean isVisible;
    private Rectangle bodySlotMachine;
    private  static int n;
    private  static int k;
    private  static ArrayList<int[]> steps;
    private Set<Updatable> thingsToUpdate;
    
    /**Cronstructor, nyadic method class, of SlotMachine.
     */
    public SlotMachine() {
        isOk = false;
        ArrayList<int[]> steps = new ArrayList<>();
        wheels = new TreeMap<>();
        thingsToUpdate = new HashSet<>();
        isVisible = false;
        bodySlotMachine = new Rectangle(10, 10, 270, 270, "pink");
        isOk = true;
    }
    
    public SlotMachine(int cant) {
        this();
        //esta parte la hizo Gemini IA, todos los posibles 50 colores
        String[] colorsHex = {
            "#FF0000", "#8B0000", "#DC143C", "#FF6347", "#FFC0CB", "#FF1493", "#C71585",
            "#FF4500", "#FF8C00", "#FFA500", "#FFD700", "#FFFF00", "#F0E68C", "#B8860B",
            "#008000", "#00FF00", "#32CD32", "#228B22", "#8FBC8F", "#00FA9A", "#6B8E23",
            "#0000FF", "#000080", "#1E90FF", "#4682B4", "#00BFFF", "#00FFFF", "#48D1CC",
            "#5F9EA0", "#800080", "#4B0082", "#8A2BE2", "#DA70D6", "#EE82EE", "#FF00FF",
            "#9370DB", "#A52A2A", "#8B4513", "#D2691E", "#F4A460", "#DEB887", "#FFDEAD",
            "#000000", "#2F4F4F", "#696969", "#808080", "#A9A9A9", "#C0C0C0", "#DCDCDC",
            "#F5F5DC"
        };
        //hasta aca
        Canvas.getCanvas().resizeAndRefresh(60*cant, 283);
        for (int i = 0; i < cant; i++) {
            int requestedPos = i; 
            addWheel(requestedPos);
            int correctedPos = adjustPosition(requestedPos, true);
            
            for (int u = 0; u < cant; u++) {
                addSymbol(correctedPos, colorsHex[u]);
            }
        }
        isOk = true;
    }
    
    /**
     * Add a wheel to this slot machine, place the roulette wheel on the left or right side.
     * @param pos pos is the position of wheel that is added to this object.
     */
    public void addWheel(int pos) { //Ayudado a perfeccionar con Gemini Pro Avanzado IA
        isOk = false;
        pos = adjustPosition(pos, true); 
        // Desplazar las llaves del mapa hacia la derecha para hacer espacio
        NavigableMap<Integer, Wheel> cutMapWheels = wheels.tailMap(pos, true);
        List<Integer> keysToShift = new ArrayList<>(cutMapWheels.keySet());
        Collections.reverse(keysToShift); // Importante: recorrer de mayor a menor para no sobrescribir
        for (Integer key : keysToShift) {
            Wheel wheelToMove = wheels.remove(key);
            wheelToMove.setPositionWheel(key + 1);
            wheels.put(key + 1, wheelToMove);
        }
        Wheel newWheelToAdd = new Wheel();
        newWheelToAdd.setPositionWheel(pos);
        wheels.put(pos, newWheelToAdd);
        
        adjustWheels(); 
        if (isVisible) {
            newWheelToAdd.makeVisible();
        }
        isOk = true;
    }
    
    /**
     * Adds a specific type of wheel to the slot machine at the given position.
     * 
     * @param type The class name of the wheel to add (e.g., "Rebel", "Lefty").
     * @param pos  The target position where the wheel should be inserted.
     */
    public void addWheel(String type, int pos) { //Ayudado a perfeccionar con Gemini 3.8 Flash
        isOk = false;
        comprovateType(type);
        pos = adjustPosition(pos, true);
        try {
            // Normaliza el nombre (ej. "rebel" -> "Rebel")
            String normalizedType = type.substring(0, 1).toUpperCase() + type.substring(1);
            String packageName = this.getClass().getPackageName();
            Class<?> wheelClass = Class.forName(packageName + "." + normalizedType);
            // Valida que la clase sea realmente una subclase de Wheel
            if (!Wheel.class.isAssignableFrom(wheelClass)) {
                errorMessage("La clase '" + normalizedType + "' no es una rueda válida.");
                ok();
                return;
            }
            Wheel newWheelToAdd = null;
            if (normalizedType.equalsIgnoreCase("Lefty")) {
                if (pos <= 1) {
                    errorMessage("Una rueda Lefty no puede ser colocada en la primera posición.");
                    ok();
                    return;
                }
                Wheel previousWheel = wheels.get(pos - 1);
                if (previousWheel == null) {
                    errorMessage("No existe una rueda previa para inicializar la rueda Lefty.");
                    ok();
                    return;
                }
                newWheelToAdd = (Wheel) wheelClass.getDeclaredConstructor(Wheel.class)
                                                  .newInstance(previousWheel);
            } else {
                newWheelToAdd = (Wheel) wheelClass.getDeclaredConstructor().newInstance();
            }
            // Una vez instanciada con éxito, se desplazan las ruedas hacia la derecha
            NavigableMap<Integer, Wheel> tailWheels = wheels.tailMap(pos, true);
            List<Integer> keysToShift = new ArrayList<>(tailWheels.keySet());
            Collections.reverse(keysToShift);
            for (Integer key : keysToShift) {
                Wheel wheelToMove = wheels.remove(key);
                wheelToMove.setPositionWheel(key + 1);
                wheels.put(key + 1, wheelToMove);
            }
            // Inserción y configuración de la nueva rueda
            newWheelToAdd.setPositionWheel(pos);
            wheels.put(pos, newWheelToAdd);
            if (Updatable.class.isAssignableFrom(wheelClass)) thingsToUpdate.add((Updatable) newWheelToAdd);// Bloque ayudado a hacer por Gemini Pro 3.1
            adjustWheels();
            if (isVisible) {
                newWheelToAdd.makeVisible();
            }
            isOk = true;
        } catch (ClassNotFoundException e) {
            errorMessage("Error: La clase de rueda '" + type + "' no existe en el proyecto.");
        } catch (NoSuchMethodException e) {
            errorMessage("Error: Constructor adecuado no encontrado para '" + type + "'.");
        } catch (Exception e) {
            errorMessage("Error al instanciar la rueda: " + e.getMessage());
        }
        ok();
    }

    /**
     * Add a symbol to a specific wheel, specifying its type (Ephemeral, Shy, Rotation).
     * @param type type is the specific kind of symbol to add (e.g., "Ephemeral", "Shy", "Rotation").
     * @param pos  pos is the position of the wheel in the slot machine.
     */
    public void addSymbol(String type, int pos, String color) { //Ayudado a hacer con Gemini 3.8 Flash
        isOk = false;
        
        // Verifica que la rueda en la posición 'pos' exista
        boolean isApprovedInvariant = proofInvariant(pos, false);
        if (!isApprovedInvariant) return;
        try {
            String packageName = this.getClass().getPackageName();
            // Asegura primera letra mayúscula por si pasan "ephemeral", "shy" o "rotation"
            String formattedType = type.substring(0, 1).toUpperCase() + type.substring(1);
            Class<?> symbolClass = Class.forName(packageName + "." + formattedType);
            Symbol newSymbolToAdd = null;
            newSymbolToAdd = (Symbol) symbolClass.getDeclaredConstructor(String.class)
                                                 .newInstance(color);
            // Obtiene la rueda y le agrega el nuevo símbolo instanciado
            Wheel wheelToAddSymbol = wheels.get(pos);
            wheelToAddSymbol.addSymbol(newSymbolToAdd);
            // Si el símbolo implementa Updatable (como Ephemeral y Shy), se registra para actualización
            if (Updatable.class.isAssignableFrom(symbolClass)) thingsToUpdate.add((Updatable) newSymbolToAdd);
            isOk = true;
        } catch (ClassNotFoundException e) {
            System.out.println("Error: The class '" + type + "' does not exist in the project.");
        } catch (Exception e) {
            System.out.println("Error while attempting to create the symbol instance: " + e.getMessage());
        }
    }
    
    /**To remove a wheel, pass its left or right position.
     * If a wheel with a wheel to its right is removed, all wheels on the right move one position to the left
     * @param pos pos is the position of wheel
     */
    public void delWheel(int pos) {//Ayudado a perfeccionar con Gemini Pro Avanzado IA
        isOk = false;
        boolean isWheelsEmpty = wheels.isEmpty();
        if (isWheelsEmpty) {
            errorMessage("Esa acción no se puede realizar.");
            return;
        }
        // Auto-ajustar la posición sin lanzar error
        pos = adjustPosition(pos, false); 
        
        Wheel wheelToDelete = wheels.get(pos);
        boolean isWheelToDeleteLocked = wheelToDelete.isLocked(), canDeleteWheel = wheelToDelete.canDelete();
        if (isWheelToDeleteLocked || canDeleteWheel) {
            errorMessage("Esa acción no se puede realizar.");
            return;
        }
        
        wheelToDelete.makeInvisible();
        wheels.remove(pos);
        if (Updatable.class.isAssignableFrom(wheelToDelete.getClass())) thingsToUpdate.remove(wheelToDelete);
        // Desplazar las llaves del mapa hacia la izquierda
        NavigableMap<Integer, Wheel> cutMapWheels = wheels.tailMap(pos, false);
        List<Integer> keysToShift = new ArrayList<>(cutMapWheels.keySet());
        for (Integer key : keysToShift) {
            Wheel wheelToMove = wheels.remove(key);
            wheelToMove.setPositionWheel(key - 1); // Actualiza internamente el atributo
            wheels.put(key - 1, wheelToMove);      // Lo guarda en la nueva posición
        }
        adjustWheels(); 
        isOk = true;
    }

    /**The symbol object is created at a specific position and color.
     * @param pos pos is the position of wheel going to add symbol.
     * @param color color is the color of symbol that going to be created.
     */
    public void addSymbol(int pos, String color) {
        boolean isAprovedInvariant = proofInvariant(pos, false);
        if (!isAprovedInvariant) return;
        Wheel wheelToAddSymbol = wheels.get(pos);
        Symbol symbolToAdd = new Symbol(color);
        wheelToAddSymbol.addSymbol(symbolToAdd);
        isOk = true;
    }

    /** The symbol; on each wheel is removed, object is deleted.
     * @param symbol symbol is the type of symbol that going to be deleted.
     */
    public void delSymbol(String symbol) {
        isOk = false;
        for (Wheel wheel : wheels.values()) {
            wheel.delSymbol(symbol);
            
        }
        
        isOk = true;
    }

    /** The symbol is added to each wheel; shape and wheel number are requested.
     * @param wheel wheel is the number (integer) of wheel that will add the symbol. 
     * @param symbol symbol is the type of symbol that will be added at the specific number wheel.
     */
    public void placeSymbol(int wheel, String symbol) {
        boolean isAprovedInvariant = proofInvariant(wheel, false);
        if (!isAprovedInvariant) {
            ok();
            return;    
        }
        Wheel wheelToPlaceSymbol = wheels.get(wheel);
        isOk = wheelToPlaceSymbol.placeSymbol(symbol); 
    }

    /**Moves a specific number of wheel.
     * @param wheel wheel indicates the number (integer) of wheel that going to be moved.
     */
    public void spin(int wheel) {
        boolean isAprovedInvariant = proofInvariant(wheel, false);
        if (!isAprovedInvariant) return;
        wheel = adjustPosition(wheel, false); 
        Wheel wheelToSpin = wheels.get(wheel);
        if (Updatable.class.isAssignableFrom(wheelToSpin.getClass())) update((Updatable) wheelToSpin);
        boolean canWheelSpin = wheelToSpin.canSpin();
        if (canWheelSpin) {
            wheelToSpin.spin();
        }
        
        isOk = canWheelSpin; // Si está bloqueada, esto será false
        ok();
    }
    

    /**Moves each of the wheels.
     */
    public void spin() {
        isOk = false;
        boolean canWheelSpin = false, allCanSpin = true;
        for (Wheel wheel : wheels.values()) {
            canWheelSpin = wheel.canSpin();
            if (canWheelSpin) {
                wheel.spin();
            } else {
                allCanSpin = false;
            }
        }
        update();
        isOk = allCanSpin;
        ok();
    }

    /**Displays all existing symbol colors in order.
     * @return A string array with exiting symbols colors of this slot machine.
     */
    public String[] symbols() {
        isOk = false;
        ArrayList<String> colorSymbols = new ArrayList<>();    
        String[] symbolsWheel = null;
        for (Wheel wheel : wheels.values()) {
            symbolsWheel = wheel.symbols();
            for (String color : symbolsWheel) {
                colorSymbols.add(color);
            }
        }
        String[] symbols = colorSymbols.toArray(new String[0]);
        isOk = true;
        return symbols;
    }

    /**Displays the number of distinct colors among the symbols on the wheel that are flipped.
     * @return Number of distinct colors of the flipped symbols.
     */
    public int distinctSymbols() {
        isOk = false;
        int countDistincSymbols = 0;
        Map<String, Integer> infoAllSymbols = getAllSymbolsAtSlotMachine();
        for (Integer numTimesColorRepeat : infoAllSymbols.values()) {
            if (numTimesColorRepeat.equals(1)) {
                countDistincSymbols++;
            }
        }
        Set<String> colorSymbols = new HashSet<>();
        String[] symbolsWheel = null;
        for (Wheel wheel : wheels.values()) {
            symbolsWheel = wheel.symbols();
            for (String color : symbolsWheel){
                colorSymbols.add(color);
            }
        }
        isOk = true;
        return countDistincSymbols;
    }
    
    private int distinctSymbolsVisible() {
        isOk = false;
        List<String> visibleColors = getColorSymbolWheels();
        Set<String> uniqueVisibleColors = new HashSet<>(visibleColors);
        isOk = true;
        return uniqueVisibleColors.size();
    }

    /**Gives all colors selected by the wheels from left to right.
     * @return return an array of string with the symbols selected at the wheels.
     */
    public String[] configuration() {
        isOk = false;
        List<String> colorSymbols = getColorSymbolWheels();
        String[] symbols = colorSymbols.toArray(new String[0]);
        isOk = true;
        return symbols;
    }

    /**Tells whether all shapes selected by the wheels are identical.
     * @return true if all shapes selected by the wheels are identical,
     * false otherwise
     */
    public boolean isJackpot() {
        isOk = false;
        List<String> colorSymbols = getColorSymbolWheels();
        String firstColor = colorSymbols.get(0);
        boolean hasTheSameColor = false;
        for (String color : colorSymbols) {
            hasTheSameColor = firstColor.equals(color);
            if (!hasTheSameColor) {
                isOk = true;
                return false;
            }
        }
        isOk = true;
        return true;
    }

    /**Makes the slot machine visible.
     */
    public void makeVisible() {
        isOk = false;
        isVisible = true;
        bodySlotMachine.makeVisible();
        for (Wheel wheel : wheels.values()) {
            wheel.makeVisible();
        }
        isOk = true;
    }

    /**Makes the slot machine invisible.
     */
    public void makeInvisible() {
        isOk = false;
        isVisible = false;
        for (Wheel wheel : wheels.values()) {
            wheel.makeInvisible();
        }
        bodySlotMachine.makeInvisible();
        isOk = true;
    }

    /**Deletes all objects and close the windows.
     */
    public void exit() {
        if (wheels != null) {
            wheels.clear();
        }
        Canvas.getCanvas().close();
        isOk = true;
    }
    
    /**Indicates whether the last operation was successful.
     * @return True if the operation was successful,
     * False otherwise.
     */
    public boolean ok() {
        if (!isOk && isVisible) {
            errorMessage("Esa acción no se puede realizar");
        }
        return isOk;
    }
    
    /**Swap two specific wheel of position 
     * @param wheel1 wheel1 is the number of first wheel at the slotmachine that will be swaped by second wheel.
     * @param wheel2 wheel2 is the nunmber of second wheel at the slotmachine that will be swaped by first wheel.
     */
    public void swap(int wheel1, int wheel2) {
        if (!proofInvariant(wheel1, false)) return;
        if (!proofInvariant(wheel2, false)) return;
        Wheel findedWheel1 = wheels.get(wheel1);
        Wheel findedWheel2 = wheels.get(wheel2);
        boolean canSwapWheels = !findedWheel1.isLocked() && !findedWheel2.isLocked();
        if (canSwapWheels) {
            findedWheel1.swap(findedWheel2);
            wheels.put(wheel1, findedWheel2);// Establecen las posiciones en el lugar correcto
            wheels.put(wheel2, findedWheel1);
            adjustWheels();
            isOk = true;
        } else {
            isOk = false; // Falla porque alguna o las dos estám bloqueada
        }
    }
    
    /**This locked a specific wheel to this wheel can't spin
     * @param wheel wheel is an integer that means the number of this slotmachine; That wheel will be locked. 
     */
    public void lock(int wheel) {
        if (!proofInvariant(wheel, false)) return;
        Wheel wheelToLock = wheels.get(wheel);
        wheelToLock.lock();
        isOk = false;
    }
    
    /**Make a wheel unlock, this able to the wheel spin corectly 
     * @param wheel wheel is an integer that means the number of this slotmachine; That wheel will be locked. 
     */
    public void unlock(int wheel) {
       if (!proofInvariant(wheel, false)) return;
        Wheel wheelToUnlock = wheels.get(wheel);
        wheelToUnlock.unlock();
        isOk = true;
    }
    
    /**Makes a update visually of SlotMachine
     * 
     */
    public void frameFlickering() {
        isOk = false;
        isVisible = true;
        bodySlotMachine.frameFlickering();
        for (Wheel wheel : wheels.values()) {
            wheel.frameFlickering();
        }
        isOk = true;
    }
    
    /**Spin a specific wheel a specific steps
     * @param wheel wheel is the number of wheel will be to spined,
     * @param steps steps is the number of symbols the specific wheel will be moved.
     */
    public void spin(int wheel, int steps) throws InterruptedException {
        if (!proofInvariant(wheel, false)) return;
        Wheel wheelToSpin = wheels.get(wheel);
        
        if (Updatable.class.isAssignableFrom(wheelToSpin.getClass())) update((Updatable) wheelToSpin);
        
        if (isVisible) {
            wheelToSpin.spinSlowly(steps);
        } else if (!isVisible) {
            wheelToSpin.spin(steps);
        }
        boolean canSpin = wheelToSpin.canSpin();
        isOk = canSpin;
        ok();
        isOk = true;
    }
    
    /**Spin the wheels to place an specific configuration given.
     * @param setSymbols setSymbols is the specific configuration that if it's possible can set, this will be to setted.
     */
    public void spin(String[] setSymbols) {
        isOk = false;
        int lengthArray = setSymbols.length;
        boolean specificInvariant = lengthArray == wheels.size();
        if (!specificInvariant) { // No cumple la condición para hacerse 
            ok();
            return;
        } 
        boolean allPlaced = true;
        String specificSymbolToPut = null;
        for (int i = 0; i < lengthArray; i++) {
            specificSymbolToPut = setSymbols[i];
            placeSymbol(i+1, specificSymbolToPut);
            allPlaced &= isOk;   // acumula si alguna falló
        }
        isOk = allPlaced;
    }
    
    public Map<Integer, Wheel> getWheels() {
        return wheels;
    }
    
    public static void main(String[] args) {
        SlotMachine game = new SlotMachine();
        game.makeVisible();
    }
    
    protected  Wheel wheelsGet(int i) {
        return wheels.get(i);
    }
    
    /* Obtains information of all symbol of all wheel (Key: Color, Value: time color repeats).
     * @return A map with the information of all symbol of SlotMachine 
     */
    private Map<String, Integer> getAllSymbolsAtSlotMachine() {
        Map<String, Integer> allSymbols = new HashMap<>();
        for (Wheel wheel : wheels.values()) {
            wheel.getInformationSymbols(allSymbols);
        }
        return allSymbols;
    }
    
    /*
     * Error message this going to apperear at the screen
     * This only apper if simulator is visible
     */
    private void errorMessage(String messageError) {
        if (isVisible) {
            JOptionPane.showMessageDialog( 
                null, 
                messageError,  // Mensaje de la ventana
                "Invalid Action",  //Mensaje del titulo de la ventana
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private boolean proofInvariant(int pos, boolean isAdding) { // Ayudado a perfeccionar con Gemini Pro Avanzado IA
        isOk = false;
        int sizeWheels = wheels.size(), plusSize = isAdding ? 1 : 0;
        boolean wheelExists = wheels.containsKey(pos), isOutBoundPos = pos <= 0 || pos > sizeWheels+plusSize;
        // Verifica posiciones negativas o saltos inválidos
        if (isOutBoundPos) {
            errorMessage("Posición inválida o no consecutiva.");
            return false;
        }
        boolean ifIsAddingIsExistsPosition = isAdding ? wheelExists : !wheelExists;
        // Valida la existencia según la acción (agregar vs modificar)
        if (ifIsAddingIsExistsPosition) {
            String messageToShow = isAdding ? "La posición ya está repetida." : "La rueda no existe.";
            errorMessage(messageToShow);
            return false;
        }
        return true; // Retorna true si pasó todas las pruebas
    }
    
    private void adjustWheels() {
        int currentX = 20;
        for (Wheel wheel : wheels.values()) {
            wheel.changePosition(currentX, 50);
            currentX += 50;
        }
    }
    
    private List<String> getColorSymbolWheels() {
        ArrayList<String> colorSymbols = new ArrayList<>();
        Symbol selectedSymbolWheel = null;
        boolean existsSelectedSymbol = false;
        String colorSelectedSymbol = null;
        for (Wheel wheel : wheels.values()) {
            selectedSymbolWheel = wheel.getSelectedSymbol();
            existsSelectedSymbol = selectedSymbolWheel != null;
            if (existsSelectedSymbol) {
                colorSelectedSymbol = selectedSymbolWheel.getColor();
                colorSymbols.add(colorSelectedSymbol);
            }
        }
        return colorSymbols;
    }
    
    // Ajusta la posición para cumplir con los límites descritos en el documento
    private int adjustPosition(int pos, boolean isAdding) { // Ayudado a hacer por Gemini IA
        int max = wheels.size() + (isAdding ? 1 : 0);
        if (max == 0) return 1; 
        
        if (pos < 1) {
            return 1;
        } else if (pos > max) {
            return max;
        }
        return pos;
    }
    
    
    // ------------------------------------------------------------------------------------
    private void different() {
        int oldK = k;
        for (int i = 2; i <= n; i++) {
            int maxKPosition = 0;
        
            for (int u = 1; u <= n; u++) {
                printOI(i, 1);
        
                if (k == 1) {
                    return;
                }
        
                if (oldK < k) {
                    maxKPosition = u;
                    oldK = k;
                }
            }
        
            if (maxKPosition != 0) {
                printOI(i, maxKPosition);
        
                if (k == 1) {
                    return;
                }
            }
    
        }
    }
    private int[] identical() {
        int[] moves = new int[n + 1];

        for (int i = 1; i < n; i++) {
            printOI(1, 1);
        
            if (k == 1) {
                return moves;
            }
        
            int oldK = k;
        
            for (int u = 2; u <= n; u++) {
                printOI(u, -i);
        
                if (k == 1) {
                    return moves;
                }
        
                if (oldK < k) {
                    moves[u] = i;
                    printOI(u, i);
        
                    if (k == 1) {
                        return moves;
                    }
        
                    break;
                } else {
                    printOI(u, i);
        
                    if (k == 1) {
                        return moves;
                    }
                }
            }
        }

        printOI(1, 1);
        
        return moves;
    }
    
    private void printOI(int i, int u) {
        steps.add(new int[]{i,u});
        Wheel targetWheel = wheels.get(i);
        targetWheel.changePositionSymbol(u);
        k = distinctSymbolsVisible();
    }
    
    private void update() {
        for (Updatable toUpdate : thingsToUpdate) {
            toUpdate.update();
        }
    }
    
    private void update(Updatable thingToUpdate) {
        if (thingsToUpdate.remove(thingToUpdate)) {
            thingToUpdate.update();
            thingsToUpdate.add(thingToUpdate);
        }
    }

    private void comprovateType(String type) {
        if (type == null || type.trim().isEmpty()) {
            errorMessage("El tipo de rueda no puede estar vacío.");
            ok();
            return;
        }
    }
}