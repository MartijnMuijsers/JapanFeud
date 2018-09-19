package nl.martijnmuijsers.japanfeud;

/**
 * ShowEmptyVersusScreenMessage (server -> client)
 */
public class ShowEmptyVersusScreenMessage extends ServerToClientMessage {
	
	public ShowEmptyVersusScreenMessage() {
		super(ServerToClientMessageType.SHOW_EMPTY_VERSUS_SCREEN);
	}
	
}
