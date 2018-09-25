/*
 * Custom class that inserts random numbers into an array passed
 */
import java.util.Random;

public class createRandomArray {
    int[] array;
    Random random = new Random();

    createRandomArray(int[] array) {
        this.array = array;
    }

    public void initializeRandomArray() {
        for(int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(10) + 1;
        }
    }
}
