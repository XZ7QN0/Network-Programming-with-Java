import java.io.*;
import javax.net.ssl.*;

public class SecureMortgageCalculatorClient {
	public final static int PORT = 7251;
	public final static String HOST = "127.0.0.1";
	
	public static void main(String[] args) {
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket = null;
		
		try {
			socket = (SSLSocket) factory.createSocket(HOST, PORT);
			
			// enable all suites
			String[] supported = socket.getSupportedCipherSuites();
			socket.setEnabledCipherSuites(supported);
			
			// Read response
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Prepares the GET
			Writer out = new OutputStreamWriter(socket.getOutputStream());
			// https requires the full URL in the GET line
			out.write("TEST");
			out.flush();
			
			// Read the header
			String s;
			
			while(!(s = reader.readLine()).equals("")) {
				System.out.println(s);
			}
			System.out.println();
			
			int length = Integer.MAX_VALUE;
			
			int c;
			int i = 0;
			while ((c = reader.read()) != -1 && i++ < length) {
				System.out.write(c);
			}
			
		} catch(IOException ex) {
			System.err.println(ex);
		}
	}
}