/**
* Program processes web server logfiles, but the only logfile currently
* residing within this directory is logfile.txt
* Processes with multithreading
****************************************************
* Original program obtained from
* Java Network Programming 4th Edition by O'Reilly
****************************************************
*/
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class PooledWeblog {

  // Variable that determines how many threads will be created for thread pool
  private final static int NUM_THREADS = 4;

  public static void main(String[] args) throws IOException {
    // Creates ExecutorService and Queue for thread pool and queueing threads
    ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
    Queue<LogEntry> results = new LinkedList<LogEntry>();

    /**
    * Prepares file to be read line by line in the for loop
    ******STREAM CHAINING******
    * FileInputStream -> InputStreamReader -> BufferedReader
    */
    try (BufferedReader in = new BufferedReader(
      new InputStreamReader(new FileInputStream("logfile.txt"), "UTF-8"));) {
        // Read each line while the line is not empty, and continue reading lines
        for (String entry = in.readLine();
        entry != null;
        entry = in.readLine()) {
          /**
          * Creates new LookupTask object, which deals with the processing
          * future: starts submission of each thread created
          * result: an object that holds each line and the thread creation
          * results: the Queue created, where each result is being added to
          */
          LookupTask task = new LookupTask(entry);
          Future<String> future = executor.submit(task);
          LogEntry result = new LogEntry(entry, future);
          results.add(result);
      }
    }

    /**
    * Loops through each result in the queue, and prints the expected results.
    * Should an exception occur, the unaltered result will print.
    * Blocks whenever a result  isn't ready.
    */
    for (LogEntry result : results) {
      try {
        System.out.println(result.future.get());
      } catch (InterruptedException | ExecutionException ex) {
        System.out.println(result.original);
      }
    }

    // Properly closes the thread pool service
    executor.shutdown();
  }

  // A class that holds the data for both the line entry and the thread created
  private static class LogEntry {
    String original;
    Future<String> future;

    LogEntry(String original, Future<String> future) {
     this.original = original;
     this.future = future;
    }
  }
}
/**
*******OUTPUT*******(within 5 seconds)
dfw25s13-in-f4.1e100.net  unknown - [17/Jun/2013:22:53:58 -0500]
"GET /bgs/greenbg.gif HTTP 1.0" 200 50
a23-72-208-104.deploy.static.akamaitechnologies.com  unknown - [17/Jun/2013:22:53:58 -0500]
"GET /bgs/greenbg.gif HTTP 1.0" 200 50
205.160.186.76  unknown - [17/Jun/2013:22:53:58 -0500]
"GET /bgs/greenbg.gif HTTP 1.0" 200 50
205.160.186.76  unknown - [17/Jun/2013:22:53:58 -0500]
"GET /bgs/greenbg.gif HTTP 1.0" 200 50
205.160.186.76  unknown - [17/Jun/2013:22:53:58 -0500]
"GET /bgs/greenbg.gif HTTP 1.0" 200 50
205.160.186.76  unknown - [17/Jun/2013:22:53:58 -0500]
"GET /bgs/greenbg.gif HTTP 1.0" 200 50
*/
