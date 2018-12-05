import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
 
public class PhonebookServer {
	public final static int PORT = 31951;
	protected Socket socket;
	protected String fileName = "phonebook.txt";
	
	public static void main(String[] args) throws Exception {
		System.out.println("Phonebook server is running...");
		
		// Create ServerSocket and socket that'll accept client's connection
		ServerSocket server = new ServerSocket(PORT);
		Socket socket = server.accept();
		
		// Running the server
		PhonebookServer phonebookServer = new PhonebookServer();
		phonebookServer.setSocket(socket);
		
		// Launches the server
		phonebookServer.launch();
		
		System.out.println("Phonebook server has now closed.");
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public void launch() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			// Reads line from client
			String line = reader.readLine();
			
			// Write results back to client
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			// Writes information to client
			// writer.write(line) writes back what client sent intially
			// TODO: Parse 'line' (spaces as delimiters)
			String[] contactInfo = line.split(" ");
			
			switch(contactInfo[0]) {
				case "GET":
					System.out.println("You have called the GET function.");
					//String results = GET(contactInfo[1]);
					writer.write(GET(contactInfo[1]));
					break;
				case "PUT":
					System.out.println("You have called the PUT function.");
					PUT(contactInfo[1], contactInfo[2]);
					writer.write("Selected PUT method");
					break;
				default:
					writer.write("Function not supported.");
			}
			
			writer.newLine();
			writer.flush();
			
			// Close stream
			reader.close();
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method to read from a file
	public String GET(String contactInfo) {
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			
			while(line != null) {
				sb.append(line);
				line = "";
				int space = line.indexOf(" ", 0);
				
				// If first string parsed equals contact info being looked for
				if(contactInfo == line.substring(0, space)) {
					break;
				} else {
					sb.append(System.lineSeparator());
					line = reader.readLine();
				}
				
			}
			
			// Something is wrong with returning line, returning a plain string works fine
			// TODO: Figure out the problem with this
			// IDEA: Print the string "line" and return some dummy string, we can figure out what line is
			// Other idea: Print the string "line" in the while loop
			return line;
		} catch (IOException ex) {
			System.err.println("File not found. Try writing to the file before reading it?");
		}
		
		return "Couldn't find the contact.";
	}
	
	// Method to write to a file
	public void PUT(String contactInfo, String phoneNumber) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
			writer.println(contactInfo + " " + phoneNumber);
			writer.close();
		} catch (IOException ex) {
			System.err.println("Could not write to file for some reason.");
		}
	}	
}