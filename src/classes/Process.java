package classes;
import enums.State;
import java.util.Random;

public class Process {
    private final int pid, totalProcessingTime;
    private int executedProcessingTime, programCounter, ioOperations, cpuUsage;
    private State state;

    public Process(int pid, int totalProcessingTime) {
        this(pid, totalProcessingTime, State.NEW);
    }

    public Process(int pid, int totalProcessingTime, State state) {
        this.pid = pid;
        this.totalProcessingTime = totalProcessingTime;
        this.programCounter = 1; //executedProcessingTime + 1.
        this.state = state;
    }

    public void process() {
        if (isTerminated()) {
            return;
        }
        executedProcessingTime++;
        programCounter++; //Next operation to be done.
        if (executedProcessingTime == totalProcessingTime) {
            terminate();
            return;
        }
        //Possible I/O operation.
        Random r = new Random();
        int pseudoRandomNumber = r.nextInt(100); //0-99
        if (pseudoRandomNumber < 5) {
            waitIO();
        }
    }

    public void checkIfDataIsAvailable() {
        Random r = new Random();
        int pseudoRandomNumber = r.nextInt(100);
        if (pseudoRandomNumber < 30) {
            getReady();
        }
    }

    public void run() {
        state = State.RUNNING;
        cpuUsage++;
    }

    public void waitIO() {
        state = State.WAITING;
        ioOperations++;
    }

    public void getReady() {
        state = State.READY;
    }

    public void terminate() {
        state = State.TERMINATED;
    }

    public boolean isTerminated() {
        return state == State.TERMINATED;
    }

    public boolean isWaiting() {
        return state == State.WAITING;
    }

    public boolean isRunning() {
        return state == State.RUNNING;
    }

    public int getPID() {
        return pid;
    }

    public State getState() {
        return state;
    }

    public String toTableFormat() {
        return String.format("%-20d%-30d%-20d%-20s%-20d%-20d", pid, executedProcessingTime, programCounter, state,
                ioOperations, cpuUsage);
    }
}
