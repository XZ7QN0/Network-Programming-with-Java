/*
-- array size of 1000
-- 5 threads share the array
each thread calculates the sum of part of the array
display sum result from each thread
add results from all threads
*/
import java.util.concurrent.*;
import java.util.Random;
/*
// First thread class, which calculates the sum of the first half of the array
class FirstThread extends Thread {
  int[] array = new int[100];
  int arraySumFirstHalf = 0;

  // Constructor
  FirstThread(int[] array, int arraySumFirstHalf) {
    this.array = array;
    this.arraySumFirstHalf = arraySumFirstHalf;
  }

  public void run() {
    arraySumFirstHalf();
  }

  public void arraySumFirstHalf() {
    for(int i = 0; i < array.length / 2; i++) {
      arraySumFirstHalf += array[i];
    }
  }

  public int getSum() {
    return arraySumFirstHalf;
  }
}

// Second thread class, which calculates the sum of the second half of the array
class SecondThread extends Thread{
  int[] array = new int[100];
  int arraySumSecondHalf = 0;

  // Constructor
  SecondThread(int[] array, int arraySumSecondHalf) {
    this.array = array;
    this.arraySumSecondHalf = arraySumSecondHalf;
  }

  public void run() {
    arraySumSecondHalf();
  }

  public void arraySumSecondHalf() {
    int sum = 0;

    for(int i = array.length / 2; i < array.length; i++) {
      arraySumSecondHalf += array[i];
    }
  }

  public int getSum() {
    return arraySumSecondHalf;
  }
}

// Main class
public class threads{
  public static void main(String args[]){

    // Variable Declarations
    int arraySumFirstHalf = 0;
    int arraySumSecondHalf = 0;
    int[] array = new int[100];
    Random random = new Random();

    System.out.println("Numbers in the array:");
    System.out.print("[ ");

    // Insert random numbers in the array
    for(int x=0; x < array.length; x++){
      array[x] = random.nextInt(10) + 1;

      if(x == array.length -1) {
        System.out.print(array[x] + " ");
      } else if (x % 10 == 0 && x != 0) {
        System.out.print(array[x] + ", \n");
      } else {
        System.out.print(array[x] + ", ");
      }

      try {
        Thread.sleep(100);
      } catch(Exception e) {
        System.out.println(e);
      }
    }
    System.out.println("]");

    // Creating threads for use of calculating sum of variables
    FirstThread thread0 = new FirstThread(array, arraySumFirstHalf);
    SecondThread thread1 = new SecondThread(array, arraySumSecondHalf);

    // Starting both threads
    thread0.start();
    thread1.start();

    System.out.println("-----------------------------------------------------");
    System.out.println("THREADS BEING CREATED, PLEASE WAIT...");
    System.out.println("-----------------------------------------------------");
    System.out.println();

    try {
      Thread.sleep(1337);
    } catch (Exception e) {
      System.out.println(e);
    }

    System.out.println("The sum of the first half of the array is: " + thread0.getSum());
    System.out.println("The sum of the second half of the array is: " + thread1.getSum());
    System.out.println("The sum of the entire array is: " + (thread0.getSum() + thread1.getSum()));
  }
}*/

public class threads {
  public static void main(String[] args) {
    int[] array = new int[10];
    Random random = new Random();

    System.out.println("Numbers in the array:");
    System.out.print("[ ");

    // Insert random numbers in the array
    for(int x=0; x < array.length; x++){
      array[x] = random.nextInt(10) + 1;

      if(x == array.length -1) {
        System.out.print(array[x] + " ");
      } else if (x % 10 == 0 && x != 0) {
        System.out.print(array[x] + ", \n");
      } else {
        System.out.print(array[x] + ", ");
      }

      try {
        Thread.sleep(100);
      } catch(Exception e) {
        System.out.println(e);
      }
    }
    System.out.println("]");

    MultithreadedSumFinder sum = new MultithreadedSumFinder();

    System.out.println("Test: " + sum.sum(array));
  }
}
