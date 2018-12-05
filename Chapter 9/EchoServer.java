import java.io.*;
import java.net.*;

public class EchoServer {
	public final static int PORT = 4000;
	
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(PORT);
		System.out.println("ECHO Server is running...");
		Socket socket = server.accept();
		
		// Reading from keyboard (keyRead object)
		BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
		
		// Sending to client (pwrite object)
		OutputStream ostream = socket.getOutputStream();
		PrintWriter pwrite = new PrintWriter(ostream, true);
		
		// Receiving from server (receiveRead object)
		InputStream istream = socket.getInputStream();
		BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
		
		String receiveMessage, sendMessage;
		String echoMessage = "ECHO Message: ";
		
		// While the message read is not null, it prints the message received
		while(true) {
			if((receiveMessage = receiveRead.readLine()) != null) {
				System.out.println(receiveMessage);
			}
			// TODO: Receive message from client, and send that same message back to the client
			//sendMessage = keyRead.readLine();
			sendMessage = receiveMessage;

			pwrite.println(echoMessage + sendMessage);
			pwrite.flush();
		}
	}
}