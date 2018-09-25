import java.util.concurrent.*;
import java.util.Arrays;

public class MultithreadedSumFinder {
    // Integer variable that dictates the amount of pieces the array should be split into
    private static int splitArray = 5;

    private static int[] sum(int[] data) throws InterruptedException, ExecutionException {
        // Creates and intializes a thread array to create the same amount of threads as the pieces of array being split
        FindSumTask[] taskList = new FindSumTask[splitArray];
        for(int i = 0; i < splitArray; i++) {
            taskList[i] = new FindSumTask(data, (i * data.length) / splitArray, ((i+1) * data.length) / splitArray);
        }

        // Creates a thread pool for each thread being made
        ExecutorService service = Executors.newFixedThreadPool(splitArray);

        // Creates and intializes a Future array and starts the submission of each thread created
        Future<Integer>[] future = new Future[splitArray];
        for(int i = 0; i < splitArray; i++) {
            future[i] = service.submit(taskList[i]);
        }

        // Creates and intializes integer array to contain results from executed threads
        int[] result = new int[splitArray];
        for(int i = 0; i < splitArray; i++) {
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
        // Declaration of array of size and a variable to contain the entire array sum value
        int[] data = new int[1000];
        int sumOfArray = 0;

        // Creation of custom random class that will be used to insert random numbers in array
        createRandomArray random = new createRandomArray(data);
        random.initializeRandomArray();

        //MultithreadedSumFinder test = new MultithreadedSumFinder();

        // Prints out the array and the data contained in the array
        System.out.println("Array has elements: " + Arrays.toString(data));
        System.out.println("Array is of size: " + data.length);

        // Initializes the calculations of the array, and gets a return of the results within an array, then
        // prints out the results of each part of the array
        sum(data);
        for(int i = 0; i < getSplitArray(); i++) {
            System.out.println("Sum of part " + (i + 1) + " of array is: " + sum(data)[i]);
        }

        // Calculates the sum of the entire array, then prints it
        for(int i = 0; i < getSplitArray(); i++) {
            sumOfArray += sum(data)[i];
        }
        System.out.println("Sum of entire array is: " + sumOfArray);
    }
}