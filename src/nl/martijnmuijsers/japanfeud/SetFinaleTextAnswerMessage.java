package nl.martijnmuijsers.japanfeud;

import java.util.List;

import lombok.Getter;

/**
 * SetFinaleTextAnswerMessage (server -> client)
 * - player number
 * - question number
 * - text answer
 */
public class SetFinaleTextAnswerMessage extends ServerToClientMessage {
	
	@Getter
	protected final int playerNumber;
	@Getter
	protected final int questionNumber;
	@Getter
	protected final String textAnswer;
	
	public SetFinaleTextAnswerMessage(int playerNumber, int questionNumber, String textAnswer) {
		super(ServerToClientMessageType.SET_FINALE_TEXT_ANSWER);
		this.playerNumber = playerNumber;
		this.questionNumber = questionNumber;
		this.textAnswer = textAnswer;
	}
	
	@Override
	public List<Object> getData() {
		List<Object> data = super.getData();
		data.add(playerNumber);
		data.add(questionNumber);
		data.add(textAnswer);
		return data;
	}
	
}
