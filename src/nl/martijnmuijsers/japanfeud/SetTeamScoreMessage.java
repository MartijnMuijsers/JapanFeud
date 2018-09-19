package nl.martijnmuijsers.japanfeud;

import java.util.List;

import lombok.Getter;

/**
 * SetTeamScoreMessage (server -> client)
 * - team number
 * - score
 */
public class SetTeamScoreMessage extends ServerToClientMessage {
	
	@Getter
	protected final int teamNumber;
	@Getter
	protected final int score;
	
	public SetTeamScoreMessage(int teamNumber, int score) {
		super(ServerToClientMessageType.SET_TEAM_SCORE);
		this.teamNumber = teamNumber;
		this.score = score;
	}
	
	@Override
	public List<Object> getData() {
		List<Object> data = super.getData();
		data.add(teamNumber);
		data.add(score);
		return data;
	}
	
}
