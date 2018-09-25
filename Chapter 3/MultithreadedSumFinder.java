/*
DIVIDE ARRAY INTO 5 PARTS
0, array.legth / 5
array.length / 5, 2*array.length / 5
2*array.length / 5, 3*array.length / 5
3*array.length / 5, 4*array.length / 5
4 * array.length / 5, array.length
*/
import java.util.concurrent.*;
import java.util.Arrays;

public class MultithreadedSumFinder{

  MultithreadedSumFinder() {

  }
  public static int sum(int[] data) throws InterruptedException, ExecutionException {

    //FindSumTask task1 = new FindSumTask(data, 0, data.length / 2);
    //FindSumTask task2 = new FindSumTask(data, data.length / 2, data.length);
    FindSumTask taskList[];

    for(int i = 0; i < data.length; i++) {
      taskList[i] = new FindSumTask(data, i, data.length / 2);
    }

    // TODO: Figure out how to successfully use ExecutorService and Future
    ExecutorService service = Executors.newFixedThreadPool(2);

    Future<Integer> future1 = service.submit(task1);
    //Future<Integer> future2 = service.submit(task2);

    service.shutdown();
    return future1.get();
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    int[] data = { 1, 5, 5, 5, 99, 7, 32, 245 };

    MultithreadedSumFinder test = new MultithreadedSumFinder();

    System.out.println("Array has elements: " + Arrays.toString(data));

    System.out.println(test.sum(data));
    //try {System.out.println("Sum: " + test.sum(data));}
    //catch(InterruptedException e) {System.out.println(e);}
  }
}
