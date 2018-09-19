package nl.martijnmuijsers.japanfeud;

/**
 * ShowFinaleChoicesMessage (server -> client)
 */
public class ShowFinaleChoicesMessage extends ServerToClientMessage {
	
	public ShowFinaleChoicesMessage() {
		super(ServerToClientMessageType.SHOW_FINALE_CHOICES);
	}
	
}
