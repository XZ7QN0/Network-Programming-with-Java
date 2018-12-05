import java.io.IOException;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

public class MortgageCalculatorClientNIO {
  // Sets host and port
  public static int DEFAULT_PORT = 31951;
  public static String DEFAULT_HOST = "127.0.0.1";

  public static void main(String[] args) throws IOException, InterruptedException {
    String passToServer = "";

    if(args.length != 3) {
      printArgumentMessage();
      return;
    } else {
      passToServer = args[0] + " " + args[1] + " " + args[2];
    }

    // Creates initial socket address and opens the channel
    InetSocketAddress address = new InetSocketAddress(DEFAULT_HOST, DEFAULT_PORT);
    SocketChannel channel = SocketChannel.open(address);

    System.out.println("Connecting to Server on port " + DEFAULT_PORT + "...");

    // Message to be sent to the server
    byte[] message = passToServer.getBytes();
    ByteBuffer buffer = ByteBuffer.wrap(message);

    channel.write(buffer);

    buffer.clear();

    // Prepares client to receive data from server
    WritableByteChannel out = Channels.newChannel(System.out);

	System.out.print("Monthly payment: ");
    int n = channel.read(buffer);

    if(n > 0) {
      buffer.flip();
      out.write(buffer);
      buffer.clear();
    }

    channel.close();
  }

  // Method to notify user that arguments are needed to continue program
  private static void printArgumentMessage() {
    System.out.println("Missing 3 arguments:\n" +
    "1) Loan Amount Required\n" +
    "2) Interest Rate Percentage\n" +
    "3) Loan Period (in months)");
  }
}
