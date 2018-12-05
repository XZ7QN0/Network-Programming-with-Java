import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.text.NumberFormat;

public class LoanCalculatorMultiThreading implements Callable<Void> {
	// Socket connection for multithreading, obtained with the connection from the Client
	private Socket connection;

    LoanCalculatorMultiThreading(Socket connection) {
		this.connection = connection;
    }

    @Override
    public Void call() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			// Reads line from client
			String line = reader.readLine();
			String[] clientDataPassed = line.split(" ");
			String returnToClient = calculateMonthlyPayment(clientDataPassed);
			
			// Write results back to client
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			
			writer.write("Monthly payment: " + returnToClient);
			writer.newLine();
			writer.flush();
			
			reader.close();
			writer.close();
			
        } catch(IOException ex) {
			System.err.println(ex);
		}
		
		return null;
    }
	
	public String calculateMonthlyPayment(String[] clientDataPassed) {
		double loanAmount = Double.valueOf(clientDataPassed[0]);//10000.25;
		double interestRate = Double.valueOf(clientDataPassed[1]) / 1200;//26.9;
		int loanPeriodMonths = Integer.parseInt(clientDataPassed[2]);//24;
		
		double monthlyPayment = (loanAmount * interestRate) / 
		(1 - (1/Math.pow((1 + interestRate), loanPeriodMonths)));
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(monthlyPayment);
		
		return moneyString;		
	}
}
