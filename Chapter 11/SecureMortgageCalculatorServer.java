import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.text.NumberFormat;

import javax.net.ssl.*;
 
public class SecureMortgageCalculatorServer {
	
	public final static int PORT = 7251;
	public final static String algorithm = "SSL";

	public static void main(String[] args) {
		try {
			SSLContext context = SSLContext.getInstance(algorithm);
			
			// The reference implementation only supports X.509 keys
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			
			// Oracle's default kind of key store
			KeyStore ks = KeyStore.getInstance("JKS");

			// For security, every key store is encrypted with a 
			// passphrase that must be provided before we can load 
			// it from disk. The passphrase is stored as a char[] array
			// so it can be wiped from memory quickly rather than
			// waiting for a garbage collector. 
			char[] password = System.console().readPassword();
			ks.load(new FileInputStream("jnp4e.keys"), password);
			kmf.init(ks, password);
			context.init(kmf.getKeyManagers(), null, null);

			// wipe the password
			Arrays.fill(password, '0');
      
			SSLServerSocketFactory factory = context.getServerSocketFactory();
     
			SSLServerSocket server = (SSLServerSocket) factory.createServerSocket(PORT);
     
			// add anonymous (non-authenticated) cipher suites
			String[] supported = server.getSupportedCipherSuites();
			String[] anonCipherSuitesSupported = new String[supported.length];      
			int numAnonCipherSuitesSupported = 0;
			
			for (int i = 0; i < supported.length; i++) {
				if (supported[i].indexOf("_anon_") > 0) {
					anonCipherSuitesSupported[numAnonCipherSuitesSupported++] = supported[i];
				}
			}  
			
			String[] oldEnabled = server.getEnabledCipherSuites();
			String[] newEnabled = new String[oldEnabled.length + 
			numAnonCipherSuitesSupported];
			
			System.arraycopy(oldEnabled, 0, newEnabled, 0, oldEnabled.length);
			
			System.arraycopy(anonCipherSuitesSupported, 0, newEnabled, 
			oldEnabled.length, numAnonCipherSuitesSupported);
			
			server.setEnabledCipherSuites(newEnabled);
     
			// Now all the set up is complete and we can focus 
			// on the actual communication. 
			while (true) {
				// This socket will be secure,
				// but there's no indication of that in the code!
				System.out.println("Server is listening for requests...");
				try (Socket theConnection = server.accept()) {
					InputStream in = theConnection.getInputStream();
					int c;
					
					while ((c = in.read()) != -1) {
						System.out.write(c);
					} 
				} catch (IOException ex) {
					ex.printStackTrace();
				} 
			} 
		} catch (IOException | KeyManagementException
		| KeyStoreException | NoSuchAlgorithmException
		| CertificateException | UnrecoverableKeyException ex) {
			ex.printStackTrace();
		} 
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