import java.text.NumberFormat;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class LoanCalculatorServer {
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
		* Creates an instance of the LoanCalculatorMultiThreading object, and submits the task
		*/	
		try(ServerSocket server = new ServerSocket(PORT)) {
			while(true) {
				try {
					System.out.println("Server ready for calculating loans!");
					Socket connection = server.accept();
					Callable<Void> task = new LoanCalculatorMultiThreading(connection);
					pool.submit(task);
					} catch(IOException ex) {
						System.err.println(ex);
					}
				}
			} catch(IOException ex) {
				System.err.println("Couldn't start server.");
			}
		/*double loanAmount = 10000.25;
		double interestRate = 26.9;
		int loanPeriodMonths = 24;
		
		double monthlyPayment = (loanAmount * interestRate) / 
		(1 - (1/Math.pow((1 + interestRate), loanPeriodMonths)));
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(monthlyPayment);
		
		System.out.println(moneyString);*/

	}
}