package nl.martijnmuijsers.japanfeud;

import java.util.List;

import lombok.Getter;

/**
 * SetFinaleScoreMessage (server -> client)
 * - player number
 * - question number
 * - score
 */
public class SetFinaleScoreMessage extends ServerToClientMessage {
	
	@Getter
	protected final int playerNumber;
	@Getter
	protected final int questionNumber;
	@Getter
	protected final int score;
	
	public SetFinaleScoreMessage(int playerNumber, int questionNumber, int score) {
		super(ServerToClientMessageType.SET_FINALE_SCORE);
		this.playerNumber = playerNumber;
		this.questionNumber = questionNumber;
		this.score = score;
	}
	
	@Override
	public List<Object> getData() {
		List<Object> data = super.getData();
		data.add(playerNumber);
		data.add(questionNumber);
		data.add(score);
		return data;
	}
	
}
