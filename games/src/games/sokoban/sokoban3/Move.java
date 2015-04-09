package games.sokoban.sokoban3;

public class Move implements IMoves {

	final Direction direction;
	final boolean isPush;

	public Move(Direction direction, boolean isPush) {
		this.direction = direction;
		this.isPush = isPush;
	}

	public Move(char c) {
		this.direction = Direction.valueOf(c);
		this.isPush = isPush(c);
	}

	public static boolean isPush(char c) {
		return Character.isUpperCase(c);
	}

	public static char toPush(char c) {
		return Character.toUpperCase(c);
	}
	
	@Override
	public String getMoves() {
		char directionChar = direction.toChar();
		if (isPush) {
			directionChar = toPush(directionChar);
		}
		return String.valueOf(directionChar);
	}
	
	@Override
	public String toString() {
		return toString();
	}
}
