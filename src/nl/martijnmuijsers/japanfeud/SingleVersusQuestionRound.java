package nl.martijnmuijsers.japanfeud;

import lombok.Getter;

public class SingleVersusQuestionRound {
	
	public SingleVersusQuestionRound(VersusRound parentRound) {
		this.parentRound = parentRound;
	}
	
	public void start() {
		question = Game.get().getNewQuestion();
		if (question == null) {
			Log.response("There were no questions left!");
			Game.get().stopRound(false);
		} else {
			Log.response("The question will be:");
			Log.response(question.getText());
			new ShowVersusQuestionToQuizmasterMessage(question.getText()).sendToAllClients();
			Log.response("The answers are:");
			question.logAnswers();
			Log.response("To show the question type 'show':");
			answerColors = new Color[question.getAnswers().length];
		}
	}
	
	private final VersusRound parentRound;
	
	@Getter
	private Question question;
	
	@Getter
	private boolean questionShown = false;
	
	private boolean hadFirstResponse = false;
	
	public boolean hadFirstResponse() {
		return hadFirstResponse;
	}
	
	private boolean hadSecondResponse = false;
	
	public boolean hadSecondResponse() {
		return hadSecondResponse;
	}
	
	public void showQuestion() {
		questionShown = true;
		new ShowVersusScreenWithQuestionMessage(question.getText()).sendToAllClients();
		Log.response("Type the color of the team who answered first [g,b] (this will show the answer boxes):");
	}
	
	@Getter
	private Color openColor = null;
	
	@Getter
	private Color playingColor = null;
	
	private Color[] answerColors;
	private int restI = 0;
	
	public boolean hasOpenColor() {
		return openColor != null;
	}
	
	public boolean hasPlayingColor() {
		return playingColor != null;
	}
	
	public void setOpenColor(Color openColor) {
		this.openColor = openColor;
		new ShowVersusScreenWithChoicesMessage().sendToAllClients();
		logFirstResponseQuery();
	}
	
	public void setFirstResponse(int i) {
		hadFirstResponse = true;
		if (i == 0) {
			new PlayWrongMessage().sendToAllClients();
		} else {
			setAnswerColor(i-1, openColor);
		}
		parentRound.scoresMayHaveBeenUpdated();
		if (i == 1) {
			skipSecondResponse();
		} else {
			Log.response("Type the number of the answer the other team gave:");
		}
		moveRestForward();
	}
	
	public void skipSecondResponse() {
		hadSecondResponse = true;
		playingColor = openColor;
		logOtherParticipants();
	}
	
	public void setPlayingColor(Color playingColor) {
		this.playingColor = playingColor;
		logOtherParticipants();
	}
	
	private void logOtherParticipants() {
		Log.response("For each of the other participants, type the number (0 for wrong) of the answer they gave, and type 'rest' or 'r' to show the answers left at the end, after 3 wrong guesses:");
	}
	
	private void logFirstResponseQuery() {
		Log.response("Type the number of the answer they gave (0 for wrong):");
	}
	
	public void setSecondResponse(int i) {
		hadSecondResponse = true;
		if (i == 0) {
			new PlayWrongMessage().sendToAllClients();
		} else {
			setAnswerColor(i-1, openColor.getOpponent());
		}
		parentRound.scoresMayHaveBeenUpdated();
		int greenScore = getScore(Color.GREEN);
		int blueScore = getScore(Color.BLUE);
		if (greenScore == 0 && blueScore == 0) {
			hadFirstResponse = false;
			hadSecondResponse = false;
			Log.response("Since no team had any point, the first team gets another try");
			logFirstResponseQuery();
			return;
		}
		if (greenScore > blueScore) {
			playingColor = Color.GREEN;
		} else if (blueScore > greenScore) {
			playingColor = Color.BLUE;
		}
		if (playingColor == null) {
			Log.response("Type the color that will be playing this round (determined by coin toss):");
		} else {
			Log.response("This round, " + playingColor + " will be playing since they had more points");
			logOtherParticipants();
		}
		moveRestForward();
	}
	
	public void setAnswerColor(int i, Color color) {
		new SetVersusChoiceMessage(i+1, question.getAnswers()[i], color).sendToAllClients();
		answerColors[i] = color;
	}
	
	public void addResponse(int i) {
		if (i == 0) {
			new PlayWrongMessage().sendToAllClients();
		} else {
			setAnswerColor(i-1, playingColor);
		}
		parentRound.scoresMayHaveBeenUpdated();
		moveRestForward();
	}
	
	public void rest() {
		setAnswerColor(restI, Color.GRAY);
		restI++;
		moveRestForward();
	}
	
	private void moveRestForward() {
		while (restI < answerColors.length && answerColors[restI] != null) {
			restI++;
		}
		if (restI == answerColors.length) { 
			stopAfterLastRest();
		}
	}
	
	private void stopAfterLastRest() {
		Log.response("Question is done");
		Log.response("Question score for the playing team (" + playingColor + ") was: " + getScore(playingColor));
		Log.response("Question score of other team (" + playingColor.getOpponent() + ") was: " + getScore(playingColor.getOpponent()));
		parentRound.currentSingleVersusQuestionRoundHasFinished();
	}
	
	public int getScore(Color color) {
		int score = 0;
		for (int i = 0; i < answerColors.length; i++) {
			if (answerColors[i] == color) {
				score += question.getAnswers()[i].getPeople();
			}
		}
		return score;
	}
	
}
