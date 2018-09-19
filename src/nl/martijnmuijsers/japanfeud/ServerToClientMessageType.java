package nl.martijnmuijsers.japanfeud;

import java.util.HashMap;
import java.util.Map;

public enum ServerToClientMessageType {
	
	SHOW_EMPTY_VERSUS_SCREEN(0),
	SET_TEAM_NAME_AND_RESET_SCORE_AND_SHOW(1),
	SHOW_VERSUS_SCREEN_WITH_QUESTION(2),
	SHOW_VERSUS_SCREEN_WITH_CHOICES(3),
	SET_VERSUS_CHOICE(4),
	PLAY_WRONG(5),
	SET_TEAM_SCORE(6),
	SHOW_EMPTY_FINALE_SCREEN(7),
	SHOW_VERSUS_QUESTION_TO_QUIZMASTER(8),
	SHOW_FINALE_QUESTIONS_TO_QUIZMASTER(9),
	SET_FINALE_TEXT_ANSWER(10),
	SET_FINALE_SCORE(11),
	HIDE_FINALE_CHOICES(12),
	SHOW_FINALE_CHOICES(13);
	
	private int id;
	
	private ServerToClientMessageType(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	private static Map<Integer, ServerToClientMessageType> BY_ID = new HashMap<>();
	
	static {
		for (ServerToClientMessageType type : values()) {
			BY_ID.put(type.getID(), type);
		}
	}
	
	public static ServerToClientMessageType getByID(int id) {
		return BY_ID.get(id);
	}
	
}
