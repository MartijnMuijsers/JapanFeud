package nl.martijnmuijsers.japanfeud;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Question {
	
	@Getter
	private final String text;
	@Getter
	private final Answer[] answers;
	
	public static final int standardAnswerAmount = 6;
	
	public static Question create(String text, List<String> answers) {
		text = text.trim();
		if (text.isEmpty()) {
			throw new IllegalArgumentException("Question text cannot be empty!");
		}
		if (answers.size() != standardAnswerAmount) {
			throw new IllegalArgumentException("Number of answers is incorrect (" + answers.size() + " instead of " + standardAnswerAmount + ")!");
		}
		Answer[] answerArray = new Answer[answers.size()];
		for (int i = 0; i < answers.size(); i++) {
			StringTokenizer s = new StringTokenizer(answers.get(i), "\t");
			answerArray[i] = Answer.create(Integer.parseInt(s.nextToken()), s.nextToken());
		}
		Arrays.sort(answerArray, Answer.LOW_TO_HIGH);
		humanize(answerArray);
		Arrays.sort(answerArray, Answer.HIGH_TO_LOW);
		return new Question(text, answerArray);
	}
	
	public void logAnswers() {
		for (int i = 0; i < 6; i++) {
			Log.response((i+1)+"\t"+answers[i].getText());
		}
	}
	
	private static void humanize(Answer[] answers) {
		for (int i = 0; i < answers.length; i++) {
			int actualPeople = answers[i].getPeople();
			double chance;
			if (actualPeople == 1) {
				chance = 0.3;
			} else if (actualPeople == 2) {
				chance = 0.6;
			} else if (actualPeople == 3) {
				chance = 0.9;
			} else {
				chance = 1;
			}
			if (Math.random() < chance) {
				answers[i].setPeople(actualPeople*2);
			}
		}
		int[] cumulativePeople = new int[answers.length];
		for (int i = 0; i < answers.length; i++) {
			cumulativePeople[i] = answers[i].getPeople();
			if (i > 0) {
				cumulativePeople[i] += cumulativePeople[i-1];
			}
		}
		int left = 40;
		int[] newPeople = new int[answers.length];
		for (int i = 0; i < answers.length; i++) {
			int self = answers[i].getPeople();
			int other = cumulativePeople[answers.length-1]-cumulativePeople[i]+self;
			newPeople[i] = (int) Math.round(((double) self)/other*left);
			while (newPeople[i] > left-(answers.length-1-i)) {
				newPeople[i]--;
			}
			if (newPeople[i] <= 0) {
				newPeople[i]++;
			}
			left -= newPeople[i];
		}
		int iterations = 60;
		for (int it = 0; it < iterations; it++) {
			int i = selectRising(answers.length);
			int j = selectRising(answers.length);
			if (i == j) {
				continue;
			}
			if (newPeople[i] == 1) {
				continue;
			}
			if (Math.random() < 0.8) {
				if (i > j) {
					int t = i;
					i = j;
					j = t;
				}
			}
			newPeople[i]--;
			newPeople[j]++;
		}
		for (int i = 1; i < answers.length; i++) {
			if (newPeople[i] == newPeople[i-1]) {
				if (Math.random() < 0.8) {
					int value;
					if (Math.random() < 0.5) {
						value = 1;
					} else if (Math.random() < 0.5) {
						value = 2;
					} else {
						value = 3;
					}
					while (newPeople[i-1] <= value) {
						value--;
					}
					newPeople[i-1] -= value;
					newPeople[i] += value;
				}
			}
		}
		for (int i = 0; i < answers.length; i++) {
			if (newPeople[i] == 1) {
				if (Math.random() < 0.4) {
					newPeople[i] = 2;
				} else if (Math.random() < 0.2) {
					newPeople[i] = 3; 
				}
			}
		}
		//System.out.println("After iterations: " + Arrays.toString(newPeople));
		for (int i = 0; i < answers.length; i++) {
			answers[i].setPeople(newPeople[i]);
		}
	}
	
	private static int selectRising(int limit) {
		for (int i = 0; i < limit; i++) {
			int left = limit-i;
			double factor = 2-((i+1)/((double) limit));
			double chance = 1d/left/factor;
		}
		return limit-1;
	}
	
}
