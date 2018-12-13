import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class CircumferenceMultithreading implements Callable<Void> {
	// Socket connection for multithreading, obtained with the connection from the Client
	private Socket connection;

    CircumferenceMultithreading(Socket connection) {
		this.connection = connection;
    }

    @Override
    public Void call() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			// Reads line from client
			String line = reader.readLine();
			String returnToClient = "Area: " + calculateArea(line) + ", Perimeter: " + calculatePerimeter(line);
			
			// Write results back to client
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			
			writer.write(returnToClient);
			writer.newLine();
			writer.flush();
			
			reader.close();
			writer.close();
			
        } catch(IOException ex) {
			System.err.println(ex);
		}
		
		return null;
    }
	
	public String calculateArea(String dataPassed) {
		double radius = Double.valueOf(dataPassed);
		
		double calculatedArea = Math.pow(radius, 2) * Math.PI;
		String area =String.valueOf(calculatedArea);
		
		return area;		
	}
	
	public String calculatePerimeter(String dataPassed) {
		double radius = Double.valueOf(dataPassed);
		double calculatedPerimeter = Math.PI * radius * 2;
		
		String perimeter = String.valueOf(calculatedPerimeter);
		
		return perimeter;		
	}
}
