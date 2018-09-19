package nl.martijnmuijsers.japanfeud;

import java.util.Collection;

public interface Round {
	
	public default void start() {
		// temporarily removed stop and skip
		//Log.response("Type 'stop' or 'skip' to force-stop this round");
		//Log.response("(Using 'skip' will cause the question to appear again later, using 'stop' will mark the question as done)");
	}
	
	public Collection<Question> getNotCompletedQuestions();
	
}
