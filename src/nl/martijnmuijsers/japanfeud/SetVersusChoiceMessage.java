package nl.martijnmuijsers.japanfeud;

import java.util.List;

import lombok.Getter;

/**
 * SetVersusChoiceMessage (server -> client)
 * - number
 * - text
 * - people
 * - color
 */
public class SetVersusChoiceMessage extends ServerToClientMessage {
	
	@Getter
	protected final int number;
	@Getter
	protected final Answer answer;
	@Getter
	protected final Color color;
	
	public SetVersusChoiceMessage(int number, Answer answer, Color color) {
		super(ServerToClientMessageType.SET_VERSUS_CHOICE);
		this.number = number;
		this.answer = answer;
		this.color = color;
	}
	
	@Override
	public List<Object> getData() {
		List<Object> data = super.getData();
		data.add(number);
		data.add(answer.getText());
		data.add(answer.getPeople());
		data.add(color.name());
		return data;
	}
	
}
