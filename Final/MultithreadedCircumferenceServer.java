import java.text.NumberFormat;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class MultithreadedCircumferenceServer {
	public final static int PORT = 3000;
	public final static int SIZE = 50;
	
	public static void main(String[] args) {
		// Creates threadpool of size 50
		ExecutorService pool = Executors.newFixedThreadPool(SIZE);

		// Multithreading pass through
		/**
		* Entire program in a nutshell
		* Creates a ServerSocket object with the PORT 3000 as argument
		* Prints a statement 
		* Creates a Socket object to accept server connections
		* Creates an instance of the CircumferenceMultithreading object, and submits the task
		*/	
		try(ServerSocket server = new ServerSocket(PORT)) {
			while(true) {
				try {
					System.out.println("Server ready for calculating circles!");
					Socket connection = server.accept();
					Callable<Void> task = new CircumferenceMultithreading(connection);
					pool.submit(task);
					} catch(IOException ex) {
						System.err.println(ex);
					}
				}
			} catch(IOException ex) {
				System.err.println("Couldn't start server.");
			}
	}
}