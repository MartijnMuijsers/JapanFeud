package nl.martijnmuijsers.japanfeud;

import java.util.List;

import lombok.Getter;

/**
 * SetTeamNameAndResetScoreAndShowMessage (server -> client)
 * - team number
 * - team name
 */
public class SetTeamNameAndResetScoreAndShowMessage extends ServerToClientMessage {
	
	@Getter
	protected final int teamNumber;
	@Getter
	protected final String teamName;
	
	public SetTeamNameAndResetScoreAndShowMessage(int teamNumber, String teamName) {
		super(ServerToClientMessageType.SET_TEAM_NAME_AND_RESET_SCORE_AND_SHOW);
		this.teamNumber = teamNumber;
		this.teamName = teamName;
	}
	
	@Override
	public List<Object> getData() {
		List<Object> data = super.getData();
		data.add(teamNumber);
		data.add(teamName);
		return data;
	}
	
}
