import java.util.concurrent.*;
import java.util.Arrays;
import java.util.Scanner;

public class MultithreadedArraySearch {
    // Integer variable that dictates the amount of pieces the array should be split into
    private static int arraySize = 5;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Declaration of array of size and user input that will search the array for input
        int[] array = new int[1000];
        int userInput;

        // Creation of custom random class object that will be used to insert random numbers in array
        createRandomArray random = new createRandomArray(array);
        Scanner reader = new Scanner(System.in);

        // Calls on method to insert random numbers in the array
        random.initializeRandomArray();
        System.out.println("---INITIALIZATION---");
        System.out.println("Array has elements: " + Arrays.toString(array));

        // Calls on method to shuffle the elements of the array in a random order
        random.shuffleArray(array);
        System.out.println("---ARRAY SHUFFLING---");
        System.out.println("Array has elements: " + Arrays.toString(array));

        // Requests user input for searching within the array
        System.out.println("\n--SEARCH CRITERIA---");
        System.out.print("Enter a number: ");
        userInput = reader.nextInt();
        reader.close(); // properly closes the reader action

        // Contains output from the 'contains' method
        int[] results = contains(array, userInput);

        /*
        * Loops through the results array and prints out the thread in which the user input was found
        * Returns only the first instance of the variable found within the slice of the array
        * --- INDEX ---
        * Index is calculated by multiplying 'i' to the array length and dividing by the array size,
        * then being added to the actual array location.
        * The array location returns the index of the array when it has been split,
        * rather than the array in its entirety, thus this calculation is necessary.
        */
        System.out.println("\n---RESULTS---");
        for(int i = 0; i < results.length; i++) {
            if(results[i] == -1) {
                System.out.println("THREAD " + (i+1) + ": Number not found");
            } else {
                System.out.println("THREAD " + (i+1) + ": Found " + userInput + " at index " + ((i * array.length) / arraySize + results[i]));
            }
        }
    }

    public static int[] contains(int[] array, int userInput) throws ExecutionException, InterruptedException {
        // Creates and intializes a thread array to create the same amount of threads as the pieces of array being split
        threadCreationSearch[] threadList = new threadCreationSearch[arraySize];
        for(int i = 0; i < arraySize; i++) {
            threadList[i] = new threadCreationSearch(array, userInput, (i * array.length) / arraySize, ((i+1) * array.length) / arraySize);
        }

        // Creates a thread pool for each thread being made
        ExecutorService service = Executors.newFixedThreadPool(arraySize);

        // Creates and intializes a Future array and starts the submission of each thread created
        Future<Integer>[] future = new Future[arraySize];
        for(int i = 0; i < arraySize; i++) {
            future[i] = service.submit(threadList[i]);
        }

        // Creates and intializes integer array to contain results from executed threads
        int[] result = new int[arraySize];
        for(int i = 0; i < arraySize; i++) {
            result[i] = future[i].get();
        }

        // Properly closes the thread pool service, and returns integer array with results
        service.shutdown();
        return result;
    }
}
