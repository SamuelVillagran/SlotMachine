package slotMachine;
import java.util.ArrayList;

/**
 * solution maraton problem
 *
 * @version 2.3
 */
public class SlotMachineContest {
    private  static int n;
    private  static int k;
    private  static ArrayList<int[]> steps;
    private static SlotMachine machine;
    
    public SlotMachineContest(int cant) {
        machine = new SlotMachine(n);
    }
    
    /**
     * @param u is the number of reels and symbols.
     * @return moves made for k = 1
     */
    public static int[][] solve(int n) {
        SlotMachineContest.n = n;
        steps = new ArrayList();
        SlotMachine machine = new SlotMachine(n);
        machine.spin();
        machine.makeInvisible();
        k = machine.distinctSymbols();
        
        if (k == 1) {
            return steps.toArray(new int[steps.size()][]);
        }
        
        different(machine);
        
        if (k == 1) {
            return steps.toArray(new int[steps.size()][]);
        }
        
        int[] moves = identical(machine);
        
        for (int u = 2; u <= n; u++) {
            int move = moves[u];
            printOI(u, -move,machine);
        }

        return steps.toArray(new int[steps.size()][]);
         
    
    }
    
    /**
     * @param u is the number of reels and symbols.
     */
    public static  void simulate(int n) {
        SlotMachineContest.n = n;
        steps = new ArrayList();
        machine = new SlotMachine(n);
        machine.spin();
        machine.makeVisible();
        k = machine.distinctSymbols();
        
        if (k == 1) {
            return;
        }
        
        different(machine);
        
        if (k == 1) {
            return;
        }
        
        int[] moves = identical(machine);
        
        if (k == 1) {
            return;
        }
        
        for (int u = 2; u <= n; u++) {
            int move = moves[u]; 
            printOI(u, -move,machine);
        }
        
    }
    
    private static  void printOI(int i, int u, SlotMachine machine) {
        steps.add(new int[]{i,u});
        Wheel targetWheel = machine.wheelsGet(i);
        targetWheel.changePositionSymbol(u);
        k = machine.distinctSymbols();
    }
    
    private static  void different(SlotMachine machine) {
        int oldK = k;
        for (int i = 2; i <= n; i++) {
            int maxKPosition = 0;
        
            for (int u = 1; u <= n; u++) {
                printOI(i, 1,machine);
        
                if (k == 1) {
                    return;
                }
        
                if (oldK < k) {
                    maxKPosition = u;
                    oldK = k;
                }
            }
        
            if (maxKPosition != 0) {
                printOI(i, maxKPosition,machine);
        
                if (k == 1) {
                    return;
                }
            }
    
        }
    }
    
    private static  int[] identical(SlotMachine machine) {
        int[] moves = new int[n + 1];

        for (int i = 1; i < n; i++) {
            printOI(1, 1,machine);
        
            if (k == 1) {
                return moves;
            }
        
            int oldK = k;
        
            for (int u = 2; u <= n; u++) {
                printOI(u, -i,machine);
        
                if (k == 1) {
                    return moves;
                }
        
                if (oldK < k) {
                    moves[u] = i;
                    printOI(u, i,machine);
        
                    if (k == 1) {
                        return moves;
                    }
        
                    break;
                } else {
                    printOI(u, i,machine);
        
                    if (k == 1) {
                        return moves;
                    }
                }
            }
        }

        printOI(1, 1,machine);
        
        return moves;
    }
    
}