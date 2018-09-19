package nl.martijnmuijsers.japanfeud;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class Server extends WebSocketServer {
	
	private static Server instance;
	
	public static Server get() {
		return instance;
	}
	
	public static final Object messageProcessingLock = new Object();
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Log.info("Starting Japan Feud server...");
		Examiner.get().loadPack();
		String serverIP = Config.get().getServerIP();
		int serverPort = Config.get().getServerPort();
		Log.info("Binding address: " + serverIP + ":" + serverPort);
		instance = new Server(new InetSocketAddress(serverIP, serverPort));
		Thread serverThread = new Thread(instance);
		serverThread.start();
		Log.info("Japan Feud server started!");
		Thread commandReaderThread = new Thread(new CommandReader());
		commandReaderThread.start();
		Log.info("Command reader started!");
		Game.get().logStartARound();
		try {
			serverThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			instance.stop();
		}
		Log.info("Closing...");
		System.exit(0);
	}
	
	public static void exit() {
		Log.info("Exiting...");
		System.exit(0);
	}
	
	public Server(InetSocketAddress address) {
		super(address);
	}
	
	@Override
	public void onOpen(WebSocket socket, ClientHandshake handshake) {
		Client client = Client.createClient(socket);
		Log.info("A socket " + socket.getRemoteSocketAddress() + " has been opened, with new client " + client);
	}
	
	@Override
	public void onClose(WebSocket socket, int code, String reason, boolean remote) {
		Client client = Client.getBySocket(socket);
		if (client == null) {
			Log.warning("The socket " + socket.getRemoteSocketAddress() + " has been closed without a known client, for reason: " + reason);
			return;
		}
		Log.info("The socket of client " + client + " has been closed for reason: " + reason);
		client.remove();
	}
	
	@Override
	public void onMessage(WebSocket socket, String message) {}
	
	@Override
	public void onError(WebSocket socket, Exception error) {
		Client client = Client.getBySocket(socket);
		if (client == null) {
			Log.warning("An error occured on the socket of client " + client + ":");
		} else {
			Log.warning("An error occured on a socket without a known client, " + socket.getRemoteSocketAddress() + ":");
		}
		error.printStackTrace();
	}
	
}
