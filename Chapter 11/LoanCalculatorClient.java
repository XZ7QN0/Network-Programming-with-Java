import java.io.*;
import java.net.*;
public class LoanCalculatorClient {
	public static void main(String[] args) throws Exception {
		String hostName = "127.0.0.1";
		Socket sock = new Socket(hostName, 3000);
		String passToServer = "";
		
		
		if (args.length != 3) {
			System.out.println("Missing 3 arguments:\n" +
			"1) Loan Amount Required\n" + "2) Interest Rate Percentage\n" + 
			"3) Loan Period (in months)");
			return;
		} else {
				passToServer = args[0] + " " + args[1] + " " + args[2];
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
