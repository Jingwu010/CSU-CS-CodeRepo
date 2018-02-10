package a2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Allow the Chatbot to be accessible over the network.  <br />
 * This class only handles one client at a time.  Multiple instances of ChatbotServer 
 * will be run on different ports with a port-based load balancer to handle multiple clients.
 * 
 * @author Jim Xu
 */
public class ChatbotServer {
	
	/**
	 * The instance of the {@link Chatbot}.
	 */
	private Chatbot chatbot;

	/**
	 * The instance of the {@link ServerSocket}.
	 */
	private ServerSocket serversocket;

	/**
	 * Constructor for ChatbotServer.
	 * 
	 * @param chatbot The chatbot to use.
	 * @param serversocket The pre-configured ServerSocket to use.
	 */
	public ChatbotServer(Chatbot chatbot, ServerSocket serversocket) {
		this.chatbot = chatbot;
		this.serversocket = serversocket;
	}
	
	/**
	 * Start the Chatbot server.  Does not return.
	 */
	public void startServer() {
		while(true) handleOneClient();
	}

	/**
	 * Handle interaction with a single client.  See assignment description.
	 */
	public void handleOneClient() {
		// If at any time the ChatbotServer gets an exception from any of the socket code, 
		// then print its stack trace to standard error, and return to startServer to wait for a new client connection.
		
		// A client connects; the SocketServer provides a Socket to ChatbotServer via the accept() method.
		Socket socket = null;
		try {
			socket = serversocket.accept();
			// The client may optionally send more strings and receive more responses, one response per input string.
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String inStr;
			while (true) {
				// The client provides a string to the Socket.
				// The ChatbotServer receives the string, and passes it to the Chatbot.
				inStr = br.readLine();
				if (inStr == null || inStr.equals("-1")) break;
				// If the Chatbot.getResponse method throws an AIException, 
				// the ChatbotServer should return the string 
				// "Got AIException: <message>" to the client
				String response;
				// The Chatbot generates a response and returns it to the ChatbotServer.
				try{
					response = chatbot.getResponse(inStr);
				} catch (AIException aie){
					response = "Got AIException: " + aie.getMessage(); 
				}
				// The ChatbotServer provides the response to the client via the Socket.
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				pw.println(response);
			}
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
            	// The client disconnects and the SocketServer goes back to waiting for another client.
            	socket.close();
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
	}
}
