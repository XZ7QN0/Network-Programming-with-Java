import java.io.*;
import java.net.*;
public class ChatClient
{
	public static void main(String[] args) throws Exception {
		String hostName = "127.0.0.1";
		Socket sock = new Socket(hostName, 3000);
		
		
		if (args.length != 3) {
			System.out.println("Missing 3 arguments:\n" +
			"1) Loan Amount Required\n" + "2) Interest Rate Percentage\n" + 
			"3) Loan Period (in months)");
			return;
			} else {
				passToServer = args[0] + " " + args[1] + " " + args[2];
			}
        
		// reading from keyboard (keyRead object)
		BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        
		// sending to client (pwrite object)
		OutputStream ostream = sock.getOutputStream();
		PrintWriter pwrite = new PrintWriter(ostream, true);

        // receiving from server ( receiveRead object)
		InputStream istream = sock.getInputStream();
		BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
		
		System.out.println("Start the chitchat, type and press Enter key");
		
		String receiveMessage, sendMessage;
		
		while(true) {
			System.out.print("Client: ");
			sendMessage = keyRead.readLine();  // keyboard reading
			pwrite.println("Client: " + sendMessage);       // sending to server
			pwrite.flush();                    // flush the data
			
			if((receiveMessage = receiveRead.readLine()) != null) { //receive from server
			System.out.println(receiveMessage); // displaying at DOS prompt
			}
		}
	}
}
