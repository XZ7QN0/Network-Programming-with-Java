
import java.net.*;
import java.util.concurrent.Callable;

public class LookupTask implements Callable<String> {

  private String line;

  // Constructor for LookupTask
  public LookupTask(String line) {
    this.line = line;
  }

  @Override
  public String call() {
    /**
    * index: gets entire string from beginning up to the first whitespace
    * address: gets IP address, which thankfully is from beginning to index
    * theRest: gets string after IP address until the end of line
    * hostname: gets the hostname from the IP address from DNS
    * returns the hostname and everything else if it works
    * returns the line passed with no modifications if it didn't work
    */
    try {
      int index = line.indexOf(' ');
      String address = line.substring(0, index);
      String theRest = line.substring(index);
      String hostname = InetAddress.getByName(address).getHostName();
      return hostname + " " + theRest;
    } catch (Exception ex) {
      return line;
    }
  }
}
