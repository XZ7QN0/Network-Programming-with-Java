import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
public
class EchoClient
{
  public
  static
  void
  main(String [] arstring)
  {
    try
    {
System.setProperty("javax.net.ssl.trustStore", "jnp4e.keys");
      System.setProperty("javax.net.ssl.keyStorePassword","password");
      SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
      SSLSocket sslsocket = (SSLSocket)sslsocketfactory.createSocket("localhost", 9999);

//String[] strongSuites = {"TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA"};
//String[] strongSuites = {"SSL_DH_anon_WITH_DES_CBC_SHA"};
   // sslsocket.setEnabledCipherSuites(strongSuites);

	String[] supported = server.getSupportedCipherSuites();
String[] anonCipherSuitesSupported = new String[supported.length];
int numAnonCipherSuitesSupported = 0;
for (int i = 0; i < supported.length; i++) {
if (supported[i].indexOf("_anon_") > 0) {
anonCipherSuitesSupported[numAnonCipherSuitesSupported++] =
supported[i];
}
}
String[] oldEnabled = server.getEnabledCipherSuites();
String[] newEnabled = new String[oldEnabled.length
+ numAnonCipherSuitesSupported];
System.arraycopy(oldEnabled, 0, newEnabled, 0, oldEnabled.length);
System.arraycopy(anonCipherSuitesSupported, 0, newEnabled,
oldEnabled.length, numAnonCipherSuitesSupported);
server.setEnabledCipherSuites(newEnabled);

      InputStream inputstream = System.in;
      InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
      OutputStream outputstream = sslsocket.getOutputStream();
      OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
      BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);
      String string = null;
      while ((string = bufferedreader.readLine()) != null)
      {
        bufferedwriter.write(string + '\n');
        bufferedwriter.flush();
      }
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }
}
