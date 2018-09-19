package nl.martijnmuijsers.japanfeud;

/**
 * PlayWrongMessage (server -> client)
 */
public class PlayWrongMessage extends ServerToClientMessage {
	
	public PlayWrongMessage() {
		super(ServerToClientMessageType.PLAY_WRONG);
	}
	
}
