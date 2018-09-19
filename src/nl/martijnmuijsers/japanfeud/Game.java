package nl.martijnmuijsers.japanfeud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;

public class Game {
	
	private static Game instance = null;
	public static Game get() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}
	
	@Getter
	private Round round = null;
	
	public boolean hasRound() {
		return round != null;
	}
	
	public void logStartARound() {
		Log.response("To start a new game, type 'start'");
	}
	
	public void startRound(Round round) {
		if (hasRound()) {
			throw new IllegalStateException("There is a round already running!");
		}
		this.round = round;
		this.round.start();
	}
	
	public void stopRound(boolean reuseQuestion) {
		Log.response("The round is stopping...");
		if (reuseQuestion) {
			Log.response("The round's question(s) is/are being rescheduled for later...");
			hadQuestions.removeAll(round.getNotCompletedQuestions());
		}
		this.round = null;
		Game.get().logStartARound();
	}
	
	public final Set<Question> hadQuestions = new HashSet<>();
	
	public Question getNewQuestion() {
		List<Question> questions = new ArrayList<>(Examiner.get().getQuestions());
		Collections.shuffle(questions);
		for (Question question : questions) {
			if (!hadQuestions.contains(question)) {
				hadQuestions.add(question);
				return question;
			}
		}
		return null;
	}
	
}
