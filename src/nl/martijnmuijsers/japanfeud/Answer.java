package nl.martijnmuijsers.japanfeud;

import java.util.Comparator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class Answer {
	
	@Getter
	private final String text;
	@Getter@Setter
	private int people;
	
	private Answer(String text, int people) {
		this.text = text;
		this.people = people;
	}
	
	public static Answer create(int people, String text) {
		text = text.trim();
		if (text.isEmpty()) {
			throw new IllegalArgumentException("Answer text cannot be empty!");
		}
		if (people <= 0) {
			throw new IllegalArgumentException("Number of people cannot be nonpositive!");
		}
		return new Answer(text, people);
	}
	
	public static final Comparator<Answer> LOW_TO_HIGH = (a1, a2) -> {
		int peopleCompare = Integer.compare(a1.people, a2.people);
		if (peopleCompare != 0) {
			return peopleCompare;
		}
		return -a1.text.compareTo(a2.text);
	};
	
	public static final Comparator<Answer> HIGH_TO_LOW = (a1, a2) -> -LOW_TO_HIGH.compare(a1, a2);
	
}
