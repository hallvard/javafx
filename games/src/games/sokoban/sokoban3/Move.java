package games.sokoban.sokoban3;

public class Move {

	final Direction direction;
	final boolean isPush;

	public Move(Direction direction, boolean isPush) {
		this.direction = direction;
		this.isPush = isPush;
	}

	public Move(char c) {
		this.direction = Direction.directionFor(c);
		this.isPush = Character.isUpperCase(c);
	}
	
	@Override
	public String toString() {
		char directionChar = direction.directionChar();
		if (isPush) {
			directionChar = Character.toUpperCase(directionChar);
		}
		return String.valueOf(directionChar);
	}
}
