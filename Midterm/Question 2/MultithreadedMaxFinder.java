import java.util.concurrent.*;
import java.util.Arrays;

public class MultithreadedMaxFinder {
    // Integer variable that dictates the amount of pieces the array should be split into
    private static int splitArray = 3;

    private static int[] max(int[] data) throws InterruptedException, ExecutionException {
        // Creates and intializes a thread array to create the same amount of threads as the pieces of array being split
        int[] result = new int[splitArray];
        FindMaxTask[] taskList = new FindMaxTask[splitArray];
        for(int i = 0; i < splitArray; i++) {
            taskList[i] = new FindMaxTask(data, (i * data.length) / splitArray, ((i+1) * data.length) / splitArray);
        }

        // Creates a thread pool for each thread being made
        ExecutorService service = Executors.newFixedThreadPool(splitArray);

        // Creates and intializes a Future array and starts the submission of each thread created
        // result array contains results from executed thread
        Future<Integer>[] future = new Future[splitArray];
        for(int i = 0; i < splitArray; i++) {
            future[i] = service.submit(taskList[i]);
            result[i] = future[i].get();
        }

        // Properly closes the thread pool service, and returns integer array with results
        service.shutdown();
        return result;
    }

    // Allows the return of the value for the split array size
    public static int getSplitArray() {
        return splitArray;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Declaration of array of size
        // max variable to capture max value from threads contained in resultsFromThread
        // resultsFromThread contains results from max value in each thread
        int[] data = new int[30];
        int max = 0;
        int[] resultsFromThread = new int[splitArray];

        // Creation of custom random class that will be used to insert random numbers in array
        createRandomArray random = new createRandomArray(data);
        random.initializeRandomArray();

        // Prints out the array and the data contained in the array
        System.out.println("Array has elements: " + Arrays.toString(data));
        System.out.println("Array is of size: " + data.length);

        // Initializes the calculations of the array, and
        // gets a return of the results within an array, then
        // prints out the results of each part of the array
        max(data);
        for(int i = 0; i < getSplitArray(); i++) {
          System.out.println("Max number of array is: " + max(data)[i]);
          resultsFromThread[i] = max(data)[i];
        }

        for(int i = 0; i < resultsFromThread.length; i++) {
          if(resultsFromThread[i] > max) max = resultsFromThread[i];
        }
        System.out.println("Max number in entire array is: " + max);
    }
}
