package nl.martijnmuijsers.japanfeud;

/**
 * ShowVersusScreenWithChoicesMessage (server -> client)
 */
public class ShowVersusScreenWithChoicesMessage extends ServerToClientMessage {
	
	public ShowVersusScreenWithChoicesMessage() {
		super(ServerToClientMessageType.SHOW_VERSUS_SCREEN_WITH_CHOICES);
	}
	
}
