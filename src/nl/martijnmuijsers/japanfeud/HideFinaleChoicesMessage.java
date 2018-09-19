package nl.martijnmuijsers.japanfeud;

/**
 * HideFinaleChoicesMessage (server -> client)
 */
public class HideFinaleChoicesMessage extends ServerToClientMessage {
	
	public HideFinaleChoicesMessage() {
		super(ServerToClientMessageType.HIDE_FINALE_CHOICES);
	}
	
}
