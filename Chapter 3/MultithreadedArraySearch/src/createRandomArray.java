/*
 * Custom class that inserts random numbers into an array passed
 */
import java.util.Random;

public class createRandomArray {
    int[] array;
    Random random = new Random();
    private static int randomizingUpTo = 10;

    createRandomArray(int[] array) {
        this.array = array;
    }

    public void initializeRandomArray() {
        for(int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(randomizingUpTo + 1);
        }
    }

    public void shuffleArray(int[] array){
        for(int i = array.length -1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }
}
