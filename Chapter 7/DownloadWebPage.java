/**
* Code obtained from:
* https://www.codejava.net/java-se/networking/java-urlconnection-and-httpurlconnection-examples
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class DownloadWebPage {
  public static void main(String[] args) {
    if(args.length < 2) {
      System.out.println("Syntax: <url> <file>");
      return;
    }

    String url = args[0];
    String filePath = args[1];

    try {
      URL urlObj = new URL(url);
      URLConnection urlCon = urlObj.openConnection();
      HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();

      // Example 2
      int responseCode = httpCon.getResponseCode();

      if(responseCode != HttpURLConnection.HTTP_OK) {
        System.out.println("Server returned response code " + responseCode +
        ". Download failed.");
        System.exit(0);
      } // End Example 2

      InputStream inputStream = urlCon.getInputStream();
      BufferedInputStream reader = new BufferedInputStream(inputStream);

      BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(filePath));

      byte[] buffer = new byte[4096];
      int bytesRead = -1;

      while((bytesRead = reader.read(buffer)) != -1) {
        writer.write(buffer, 0, bytesRead);
      }

      writer.close();
      reader.close();

      System.out.println("Web page saved");
    } catch(MalformedURLException e) {
      System.out.println("The specified URL is malformed: " + e.getMessage());
    } catch(IOException e) {
      System.out.println("An I/O error occurs: " + e.getMessage());
    }
  }
}
