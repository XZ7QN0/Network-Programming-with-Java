import java.net.*;
import java.io.*;

public class hostnameSearchIP {
  public static void main(String[] args) throws IOException {
    try(BufferedReader in = new BufferedReader(
    new FileReader("hostnames.txt"));) {
      for(String entry = in.readLine(); entry != null; entry = in.readLine()) {
        try {
          InetAddress[] addresses = InetAddress.getAllByName(entry);
          for(InetAddress address : addresses) {
            System.out.println(address);
          }
        } catch(UnknownHostException ex) {
          System.out.println("Could not find IP address for the host.");
        }
      }
    }
  }
}
