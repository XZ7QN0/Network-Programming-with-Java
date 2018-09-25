/*
 * ---CALLABLE---
 * Creates a Callable thread process that will create a thread and search for an integer contained in the array
 * given by user input.
 */
import java.util.concurrent.Callable;

public class threadCreationSearch implements Callable<Integer> {
    private int[] data;
    private int search;
    private int start;
    private int end;

    threadCreationSearch(int[] data, int search, int start, int end) {
        this.data = data;
        this.search = search;
        this.start = start;
        this.end = end;
    }

    public Integer call() {
        int index = 0;

        for (int n = start; n < end; n++) {
            if (search == data[n]) return index;
            index++;
        }
        return -1;
    }
}
