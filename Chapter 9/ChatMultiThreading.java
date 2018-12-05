import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatMultiThreading implements Callable<Void> {
	// Socket connection for multithreading, obtained with the connection from the Client
	private Socket connection;

    ChatMultiThreading(Socket connection) {
		this.connection = connection;
    }

    @Override
    public Void call() {
		try {
			Writer out = new OutputStreamWriter(connection.getOutputStream());

			// reading from keyboard (keyRead object)
			BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));

			// sending to client (pwrite object)
			OutputStream ostream = connection.getOutputStream();
			PrintWriter pwrite = new PrintWriter(ostream, true);

			// receiving from server (receiveRead object)
			InputStream istream = connection.getInputStream();
			BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

			String receiveMessage, sendMessage;

			while(true) {
				if((receiveMessage = receiveRead.readLine()) != null) {
					System.out.println(receiveMessage);
				}

            System.out.print("Server: ");
            sendMessage = keyRead.readLine();
            pwrite.println("Server: " + sendMessage);
            pwrite.flush();
			}
        } catch(IOException ex) {
			System.err.println(ex);
		}
		
		return null;
    }
}
