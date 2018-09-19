package nl.martijnmuijsers.japanfeud;

import java.util.List;

import lombok.Getter;

/**
 * ShowVersusQuestionToQuizmasterMessage (server -> client)
 * - question text
 */
public class ShowVersusQuestionToQuizmasterMessage extends ServerToClientMessage {
	
	@Getter
	protected final String text;
	
	public ShowVersusQuestionToQuizmasterMessage(String text) {
		super(ServerToClientMessageType.SHOW_VERSUS_QUESTION_TO_QUIZMASTER);
		this.text = text;
	}
	
	@Override
	public List<Object> getData() {
		List<Object> data = super.getData();
		data.add(text);
		return data;
	}
	
}
