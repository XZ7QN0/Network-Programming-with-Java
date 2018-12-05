import java.io.*;
import java.net.*;
public class EchoClient
{
  public static void main(String[] args) throws Exception
  {
    String hostName = "127.0.0.1";
	int port = 4000;
    Socket sock = new Socket(hostName, port);
                               // reading from keyboard (keyRead object)
    BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
                              // sending to client (pwrite object)
    OutputStream ostream = sock.getOutputStream();
    PrintWriter pwrite = new PrintWriter(ostream, true);

                              // receiving from server ( receiveRead  object)
    InputStream istream = sock.getInputStream();
    BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

    System.out.println("Start the chitchat, type and press Enter key");

    String receiveMessage, sendMessage;
    while(true) {
      System.out.print("Message to send to the server: ");
      sendMessage = keyRead.readLine();  // keyboard reading
      System.out.println();
	  pwrite.println(sendMessage);       // sending to server
      pwrite.flush();                    // flush the data
      if((receiveMessage = receiveRead.readLine()) != null) { //receive from server
        System.out.println(receiveMessage + "\n"); // displaying at DOS prompt
      }
    }
  }
}
