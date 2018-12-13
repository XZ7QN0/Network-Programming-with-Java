import java.io.*;
import java.net.*;
public class MultithreadedCircumferenceClient {
	public static void main(String[] args) throws Exception {
		String hostName = "127.0.0.1";
		Socket sock = new Socket(hostName, 3000);
		String passToServer = "";
			
		if (args.length != 1) {
			System.out.println("Missing 1 argument:\n" +
			"1) Radius required\n");
			return;
		} else {
				passToServer = args[0];
			}
		
		BufferedWriter writer = new BufferedWriter(
		new OutputStreamWriter(sock.getOutputStream()));
		
		writer.write(passToServer);
		writer.newLine();
		writer.flush();
		
		BufferedReader reader = new BufferedReader(
		new InputStreamReader(sock.getInputStream()));
		
		System.out.println(reader.readLine());
		
		reader.close();
		writer.close();
	}
}