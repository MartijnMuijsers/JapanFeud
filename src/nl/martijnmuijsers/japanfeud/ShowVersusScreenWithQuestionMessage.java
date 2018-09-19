package nl.martijnmuijsers.japanfeud;

import java.util.List;

import lombok.Getter;

/**
 * ShowVersusScreenWithQuestionMessage (server -> client)
 * - question text
 */
public class ShowVersusScreenWithQuestionMessage extends ServerToClientMessage {
	
	@Getter
	protected final String text;
	
	public ShowVersusScreenWithQuestionMessage(String text) {
		super(ServerToClientMessageType.SHOW_VERSUS_SCREEN_WITH_QUESTION);
		this.text = text;
	}
	
	@Override
	public List<Object> getData() {
		List<Object> data = super.getData();
		data.add(text);
		return data;
	}
	
}
