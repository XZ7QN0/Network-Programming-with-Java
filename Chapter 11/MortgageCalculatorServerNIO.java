import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.text.NumberFormat;

public class MortgageCalculatorServerNIO {
	public static void main(String[] args) throws IOException {
    // Starts the channel up
    ServerSocketChannel socket = ServerSocketChannel.open();
    InetSocketAddress address = new InetSocketAddress("localhost", 31951);

    // Binds channel's socket to local address, and adjusts channel's blocking mode
    socket.bind(address);
    socket.configureBlocking(false);

		// Selector: multiplexor of SelectableChannel objects
		Selector selector = Selector.open(); // selector is open here

		int ops = socket.validOps();
		SelectionKey selectKey = socket.register(selector, ops, null);

		while (true) {
			System.out.println("Awaiting connections...");

			// Selects a set of keys whose corresponding channels are ready for I/O operations
			selector.select();

			// token representing the registration of a SelectableChannel with a Selector
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = readyKeys.iterator();

			while (iterator.hasNext()) {
				SelectionKey myKey = iterator.next();

				// Tests whether this key's channel is ready to accept a new socket connection
				if (myKey.isAcceptable()) {
					SocketChannel client = socket.accept();

					// Adjusts this channel's blocking mode to false
					client.configureBlocking(false);

					// Operation-set bit for read operations
					client.register(selector, SelectionKey.OP_READ);

          ByteBuffer buffer = ByteBuffer.allocate(256);
          buffer.put((byte) 'X');
          buffer.flip();

          System.out.println("ACCEPTED CONNECTIONS: " +
          client.getLocalAddress() + "\n");

					// Tests whether this key's channel is ready for reading
				} else if (myKey.isReadable()) {
					SocketChannel client = (SocketChannel) myKey.channel();
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					client.read(buffer);
					String result = new String(buffer.array()).trim();

					System.out.println("Message received: " + result);

          // Flips buffer and sends message back to client
          //buffer.flip();
		  
		  String test = bb_to_str(buffer);//"This is a test";
		  String[] clientDataPassed = test.split(" ");
		  //System.out.println(clientDataPassed[0] + "\n" + clientDataPassed[1] + "\n" + clientDataPassed[2] );
		  String returnToClient = calculateMonthlyPayment(clientDataPassed);
		  
		  byte[] bytes = returnToClient.getBytes();
		  
          client.write(ByteBuffer.wrap(bytes));

          // Clears buffer and closes current connection to wait for new connections
          buffer.clear();
          client.close();
				}
				iterator.remove();
			}
		}
	}

  // Calculates the monthly payment
  // TODO: Calculate, and convert to bytes so it can be passed to client.write
  public static String calculateMonthlyPayment(String[] clientDataPassed) {
		double loanAmount = Double.valueOf(clientDataPassed[0]);//10000.25;
		double interestRate = Double.valueOf(clientDataPassed[1]) / 1200;//26.9;
		int loanPeriodMonths = Integer.parseInt(clientDataPassed[2].trim());//24;
		
		double monthlyPayment = (loanAmount * interestRate) / 
		(1 - (1/Math.pow((1 + interestRate), loanPeriodMonths)));
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(monthlyPayment);
		
		return moneyString;		
	}
	
	public static String bb_to_str(ByteBuffer buffer){
		byte[] bytes;
		
		if(buffer.hasArray()) {
			bytes = buffer.array();
		} else {
			bytes = new byte[buffer.remaining()];
			buffer.get(bytes);
		}
		
		return new String(bytes);
	}
}
