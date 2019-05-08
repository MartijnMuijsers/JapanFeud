package nl.martijnmuijsers.japanfeud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CommandReader implements Runnable {
	
	@Override
	public void run() {
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				String line = r.readLine();
				if (line.trim().isEmpty()) {
					continue;
				}
				StringTokenizer s = new StringTokenizer(line);
				synchronized (Server.messageProcessingLock) {
					if (Game.get().hasRound()) {
						if (line.equalsIgnoreCase("stop")) {
							Log.info("Stopping is disabled at the moment, sorry");
							//Game.get().stopRound(false);
						} else if (line.equalsIgnoreCase("skip")) {
							Log.info("Skipping is disabled at the moment, sorry");
							//Game.get().stopRound(true);
						} else {
							Round round = Game.get().getRound();
							if (round instanceof VersusRound) {
								VersusRound versusRound = (VersusRound) round;
								if (versusRound.isWaitingForScreenClear()) {
									if (line.equalsIgnoreCase("clear")) {
										versusRound.ended();
									}
								} else if (!versusRound.hadTeamName(1)) {
									if (line.contains("|")) {
										Log.logInvalidDueToDelimiter();
									} else {
										versusRound.setTeamName(1, line);
									}
								} else if (!versusRound.hadTeamName(2)) {
									if (line.contains("|")) {
										Log.logInvalidDueToDelimiter();
									} else {
										versusRound.setTeamName(2, line);
									}
								} else {
									SingleVersusQuestionRound singleVersusQuestionRound = versusRound.getCurrentSingleVersusRound();
									if (!singleVersusQuestionRound.isQuestionShown()) {
										if (line.equalsIgnoreCase("show")) {
											singleVersusQuestionRound.showQuestion();
										}
									} else if (!singleVersusQuestionRound.hasOpenColor()) {
										Color color = Color.parse(line);
										if (color != null && color.isTeam()) {
											singleVersusQuestionRound.setOpenColor(color);
										} else {
											Color.logInvalid();
										}
									} else if (!singleVersusQuestionRound.hadFirstResponse()) {
										Integer num = null;
										try {
											num = Integer.parseInt(line);
										} catch (Exception e) {}
										if (num != null && num >= 0 && num <= singleVersusQuestionRound.getQuestion().getAnswers().length) {
											singleVersusQuestionRound.setFirstResponse(num);
										} else {
											Log.logInvalidNumber();
										}
									} else if (!singleVersusQuestionRound.hadSecondResponse()) {
										if (line.equalsIgnoreCase("skip")) {
											singleVersusQuestionRound.skipSecondResponse();
										} else {
											Integer num = null;
											try {
												num = Integer.parseInt(line);
											} catch (Exception e) {}
											if (num != null && num >= 0 && num <= singleVersusQuestionRound.getQuestion().getAnswers().length) {
												singleVersusQuestionRound.setSecondResponse(num);
											} else {
												Log.logInvalidNumber();
											}
										}
									} else if (!singleVersusQuestionRound.hasPlayingColor()) {
										Color color = Color.parse(line);
										if (color != null && color.isTeam()) {
											singleVersusQuestionRound.setPlayingColor(color);
										} else {
											Color.logInvalid();
										}
									} else if (line.equalsIgnoreCase("rest") || line.equalsIgnoreCase("r")) {
										singleVersusQuestionRound.rest();
									} else {
										Integer num = null;
										try {
											num = Integer.parseInt(line);
										} catch (Exception e) {}
										if (num != null && num >= 0 && num <= singleVersusQuestionRound.getQuestion().getAnswers().length) {
											singleVersusQuestionRound.addResponse(num);
										} else {
											Log.logInvalidNumber();
										}
									}
								}
							}
						}
					}
				} else {
					if (line.equalsIgnoreCase("start") || line.equalsIgnoreCase("s")) {
						Game.get().startRound(new VersusRound());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Even though an exception occured, the application recovered, probably without damage");
		}
	}
}
