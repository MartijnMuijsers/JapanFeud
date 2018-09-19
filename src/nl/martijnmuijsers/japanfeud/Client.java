package nl.martijnmuijsers.japanfeud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.java_websocket.WebSocket;

import lombok.Getter;

public class Client {
	
	private final int id;
	@Getter
	private final WebSocket socket;
	
	private Client(int id, WebSocket socket) {
		this.id = id;
		this.socket = socket;
		ALL.add(this);
		BY_ID.put(id, this);
		BY_SOCKET.put(socket, this);
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Client) {
			Client other = (Client) obj;
			return other.id == id;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public String toString() {
		return ""+id;
	}
	
	private static List<Client> ALL = new ArrayList<>();
	private static Map<Integer, Client> BY_ID = new HashMap<>();
	private static Map<WebSocket, Client> BY_SOCKET = new HashMap<>();
	
	public static Iterable<Client> getAll() {
		return ALL;
	}
	
	/**
	 * @return null if no player with the given id exists
	 */
	public static Client getByID(int id) {
		return BY_ID.get(id);
	}
	
	/**
	 * @return null if no player with the given socket exists
	 */
	public static Client getBySocket(WebSocket socket) {
		return BY_SOCKET.get(socket);
	}
	
	public void remove() {
		ALL.remove(this);
		BY_ID.remove(id);
		BY_SOCKET.remove(socket);
	}
	
	private static int lastID = -1;
	
	public static Client createClient(WebSocket socket) {
		lastID++;
		return new Client(lastID, socket);
	}
	
}
