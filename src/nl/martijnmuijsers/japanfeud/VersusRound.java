package nl.martijnmuijsers.japanfeud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VersusRound implements Round {
	
	@Override
	public void start() {
		Log.response("Versus round started");
		new ShowEmptyVersusScreenMessage().sendToAllClients();
		Round.super.start();
		Log.response("Type the name of the " + Color.GREEN + " team:");
	}
	
	private String[] teamNames = new String[2];
	private int[] teamScores = new int[2];
	private int questionNumber = 1;
	
	private boolean waitingForScreenClear = false;
	
	public boolean isWaitingForScreenClear() {
		return waitingForScreenClear;
	}
	
	private SingleVersusQuestionRound currentSingleVersusQuestionRound;
	
	public boolean hadTeamName(int teamNumber) {
		return teamNames[teamNumber-1] != null;
	}
	
	public void setTeamName(int teamNumber, String teamName) {
		teamNames[teamNumber-1] = teamName;
		new SetTeamNameAndResetScoreAndShowMessage(teamNumber, teamName).sendToAllClients();
		if (teamNumber == 1) {
			Log.response("Type the name of the " + Color.BLUE + " team:");
		} else {
			startSingleVersusQuestionRound();
		}
	}
	
	private void startSingleVersusQuestionRound() {
		currentSingleVersusQuestionRound = new SingleVersusQuestionRound(this);
		currentSingleVersusQuestionRound.start();
	}
	
	public SingleVersusQuestionRound getCurrentSingleVersusRound() {
		return currentSingleVersusQuestionRound;
	}
	
	public void ended() {
		Game.get().stopRound(false);
	}
	
	public void currentSingleVersusQuestionRoundHasFinished() {
		teamScores[0] += currentSingleVersusQuestionRound.getScore(Color.GREEN);
		teamScores[1] += currentSingleVersusQuestionRound.getScore(Color.BLUE);
		if (questionNumber == 5) {
			Log.response("Stopping versus round, it's done");
			Log.response("Final scores:");
			Log.response("* " + Color.GREEN + " " + teamScores[0]);
			Log.response("* " + Color.BLUE + " " + teamScores[1]);
			Log.response("Type 'clear' to clear the screen");
			waitingForScreenClear = true;
		} else {
			questionNumber++;
			Log.response("Going to next question (" + questionNumber + " / 5)");
			startSingleVersusQuestionRound();
		}
	}
	
	public void scoresMayHaveBeenUpdated() {
		new SetTeamScoreMessage(1, teamScores[0]+currentSingleVersusQuestionRound.getScore(Color.GREEN)).sendToAllClients();
		new SetTeamScoreMessage(2, teamScores[1]+currentSingleVersusQuestionRound.getScore(Color.BLUE)).sendToAllClients();
	}
	
	@Override
	public Collection<Question> getNotCompletedQuestions() {
		List<Question> questions = new ArrayList<>();
		if (currentSingleVersusQuestionRound == null) {
			questions.add(currentSingleVersusQuestionRound.getQuestion());
		}
		return questions;
	}
	
}
