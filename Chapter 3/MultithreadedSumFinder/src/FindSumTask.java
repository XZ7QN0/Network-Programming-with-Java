/*
 * ---CALLABLE---
 * Creates a Callable thread process that will create a thread and search for an integer contained in the array
 * given by user input.
 */
import java.util.concurrent.Callable;

class FindSumTask implements Callable<Integer> {
    private int[] data;
    private int start;
    private int end;

    FindSumTask(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    public Integer call() {
        int sum = 0;
        for(int i = start; i < end; i++) {
            sum += data[i];
        }
        return sum;
    }
}