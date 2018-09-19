package nl.martijnmuijsers.japanfeud;

import java.util.function.Supplier;

import lombok.Getter;

public enum Color {
	
	GREEN(true, () -> valueOf("BLUE")),
	BLUE(true, () -> valueOf("GREEN")),
	GRAY(false, null);
	
	@Getter
	private boolean isTeam;
	private Supplier<Color> opponentSupplier;
	
	public Color getOpponent() {
		if (opponentSupplier == null) {
			return null;
		}
		return opponentSupplier.get();
	}
	
	private Color(boolean isTeam, Supplier<Color> opponentSupplier) {
		this.isTeam = isTeam;
		this.opponentSupplier = opponentSupplier;
	}
	
	public static Color parse(String line) {
		try {
			return Color.valueOf(line.toUpperCase());
		} catch (Exception e) {
			for (Color color : Color.values()) {
				if (color.name().substring(0, 1).equalsIgnoreCase(line.substring(0, 1))) {
					return color;
				}
			}
			return null;
		}
	}
	
	public static void logInvalid() {
		Log.response("Color is invalid!");
	}
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
	
}
