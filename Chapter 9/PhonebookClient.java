import java.net.Socket;
import java.io.*;

public class PhonebookClient {
	public static void main(String[] args) {
		String hostname = "127.0.0.1";
		int port = 31951;
		
		// Optimization necessary
		String phonebookMethod = args.length > 0 ? args[0] : "";
		String contact = args.length > 1 ? args[1] : "";
		String phoneNumber = args.length > 2 ? args[2] : "";
		String emptySpace = " ";
		
		String entry = phonebookMethod + emptySpace + contact + emptySpace + phoneNumber;
		
		try {
			Socket socket = new Socket(hostname, port);
			
			BufferedWriter writer = new BufferedWriter(
			new OutputStreamWriter(socket.getOutputStream()));
			writer.write(entry);
			writer.newLine();
			writer.flush();
			
			BufferedReader reader = new BufferedReader(
			new InputStreamReader(socket.getInputStream()));
			
			System.out.println(reader.readLine());
			
			reader.close();
			writer.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}