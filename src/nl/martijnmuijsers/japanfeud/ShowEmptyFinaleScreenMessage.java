package nl.martijnmuijsers.japanfeud;

import java.util.List;

import lombok.Getter;

/**
 * ShowEmptyFinaleScreenMessage (server -> client)
 * - playing team number
 */
public class ShowEmptyFinaleScreenMessage extends ServerToClientMessage {
	
	@Getter
	protected final int playingTeamNumber;
	
	public ShowEmptyFinaleScreenMessage(int playingTeamNumber) {
		super(ServerToClientMessageType.SHOW_EMPTY_FINALE_SCREEN);
		this.playingTeamNumber = playingTeamNumber;
	}
	
	@Override
	public List<Object> getData() {
		List<Object> data = super.getData();
		data.add(playingTeamNumber);
		return data;
	}
	
}
