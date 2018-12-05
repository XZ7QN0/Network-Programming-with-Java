import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class EchoServer
{
  public static void main(String [] arstring)
  {
    try
    {
      System.setProperty("javax.net.ssl.trustStore", "jnp4e.keys");
      System.setProperty("javax.net.ssl.keyStorePassword","password");

      SSLServerSocketFactory sslserversocketfactory =
        (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
      SSLServerSocket sslserversocket =
        (SSLServerSocket)sslserversocketfactory.createServerSocket(9999);

//String[] strongSuites = {"TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA"};
//String[] strongSuites = {"SSL_DH_anon_WITH_DES_CBC_SHA"};
    //sslserversocket.setEnabledCipherSuites(strongSuites);
	
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


      SSLSocket sslsocket = (SSLSocket)sslserversocket.accept();
      InputStream inputstream = sslsocket.getInputStream();
      InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
      String string = null;
      while ((string = bufferedreader.readLine()) != null)
      {
        System.out.println(string);
        System.out.flush();
      }
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }
}
