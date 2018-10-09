/**
* Program processes web server logfiles, but the only logfile currently
* residing within this directory is logfile.txt
* Program runs rather slow.
****************************************************
* Original program obtained from
* Java Network Programming 4th Edition by O'Reilly
****************************************************
*/

import java.io.*;
import java.net.*;

public class Weblog {

  public static void main(String[] args) {
    /**
    * Prepares file to be read line by line in the for loop
    ******STREAM CHAINING******
    * FileInputStream -> InputStreamReader -> BufferedReader
    */
    try (FileInputStream fin =  new FileInputStream("logfile.txt");
      Reader in = new InputStreamReader(fin);
      BufferedReader bin = new BufferedReader(in);) {

      // Read each line while the line is not empty, and continue reading lines
      for (String entry = bin.readLine();
        entry != null;
        entry = bin.readLine()) {

        /**
        * index: gets entire string from beginning up to the first whitespace
        * ip: gets IP address, which thankfully is from beginning to index
        * theRest: gets string after IP address until the end of line
        */
        int index = entry.indexOf(' ');
        String ip = entry.substring(0, index);
        String theRest = entry.substring(index);

        /**
        * If the host can be found from the IP address though DNS servers,
        * it prints the hostname as well as the remainder of the string.
        * However, should the hostname be unobtainable from the IP address,
        * it will simply print the entire line.
        */
        try {
          InetAddress address = InetAddress.getByName(ip);
          System.out.println(address.getHostName() + theRest);
        } catch (UnknownHostException ex) {
          System.err.println(entry); // prints entire line if host not found
        }
      } // end for loop

    } catch (IOException ex) {
      System.out.println("Exception: " + ex);
    }
  }
}
/**
*******OUTPUT*******(within 5 seconds)
dfw25s13-in-f4.1e100.net unknown - [17/Jun/2013:22:53:58 -0500]
"GET /bgs/greenbg.gif HTTP 1.0" 200 50
a23-72-208-104.deploy.static.akamaitechnologies.com unknown - [17/Jun/2013:22:53:58 -0500]
"GET /bgs/greenbg.gif HTTP 1.0" 200 50
205.160.186.76 unknown - [17/Jun/2013:22:53:58 -0500]
"GET /bgs/greenbg.gif HTTP 1.0" 200 50
205.160.186.76 unknown - [17/Jun/2013:22:53:58 -0500]
"GET /bgs/greenbg.gif HTTP 1.0" 200 50
*/
