package nl.martijnmuijsers.japanfeud;

import java.util.ArrayList;
import java.util.List;

import org.java_websocket.WebSocket;

import lombok.Getter;

public class ServerToClientMessage extends Message {
	
	@Getter
	protected ServerToClientMessageType type;
	
	protected ServerToClientMessage(ServerToClientMessageType type) {
		super(type.getID());
		this.type = type;
	}
	
	private void send(WebSocket socket) {
		if (socket.isOpen()) {
			socket.send(toString());
		}
	}
	
	public void send(Client client) {
		send(client.getSocket());
	}
	
	public void send(Iterable<Client> clients) {
		for (Client client : clients) {
			send(client);
		}
	}
	
	public void sendToAllClients() {
		send(Client.getAll());
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("");
		builder.append(id);
		for (Object data : getData()) {
			builder.append(Message.DELIMITER).append(data);
		}
		return builder.toString();
	}
	
	protected List<Object> getData() {
		return new ArrayList<>();
	}
	
}
