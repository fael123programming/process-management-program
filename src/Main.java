import classes.Process;
import classes.ProcessTable;

public class Main {
    public static void main(String[] args) {
        Process p0 = new Process(0, 10000);
        Process p1 = new Process(1, 5000);
        Process p2 = new Process(2, 7000);
        Process p3 = new Process(3, 3000);
        Process p4 = new Process(4, 3000);
        Process p5 = new Process(5, 8000);
        Process p6 = new Process(6, 2000);
        Process p7 = new Process(7, 5000);
        Process p8 = new Process(8, 4000);
        Process p9 = new Process(9, 10000);
        ProcessTable processTable = new ProcessTable(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        final int QUANTUM = 1000;
        Process current;
        System.out.println("-".repeat(150));
        System.out.printf("%-20s%-30s%-20s%-20s%-20s%-20s%s%n", "PID", "Executed Processing Time", "PC", "State", "I/O",
                "CPU", "Transition");
        System.out.println("-".repeat(150));
        while (processTable.isThereProcessNotTerminated()) {
            current = processTable.next(); //Returns the next unfinished process.
            if (current.isWaiting()) { //If the current process is waiting an I/O operation to get done.
                current.checkIfDataIsAvailable();
                System.out.println(current.toTableFormat() + "WAITING -> " + current.getState());
                continue;
            }
            current.run(); //The process is loaded into CPU for processing.
            System.out.println(current.toTableFormat() + "READY -> RUNNING");
            for (int i = 0; i < QUANTUM; i++) { //Process is processing.
                current.process();
                if (current.isTerminated() || current.isWaiting()) {
                    //Context change because process has executed everything it needs or an I/O operation came in.
                    break;
                }
            }
            if (current.isRunning()) { //QUANTUM has been fully consumed and process still has things to do.
                current.getReady();
            }
            System.out.println(current.toTableFormat() + "RUNNING -> " + current.getState());
        }
    }
}
