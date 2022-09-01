package classes;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class ProcessTable {
    private final List<Process> table;
    private int index;

    {
        table = new ArrayList<>();
    }

    public ProcessTable(Process... processes) {
        Collections.addAll(table, processes);
    }

    public Process next() {
        Process p;
        int tableSize = table.size();
        for (int i = 0; i < tableSize; i++) {
            p = table.get(index);
            index = index < tableSize - 1 ? index + 1 : 0;
            if (!p.isTerminated()) {
                return p;
            }
        }
        return null;
    }

    public boolean isThereProcessNotTerminated() {
        for (Process p : table) {
            if (!p.isTerminated()) {
                return true;
            }
        }
        return false;
    }
}
