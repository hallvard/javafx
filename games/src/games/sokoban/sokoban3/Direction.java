package games.sokoban.sokoban3;

public class Direction {

	public final int dx, dy;

	private Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	@Override
	public String toString() {
		return toChar() + ":" + dx + "," + dy;
	}
	
	public final static Direction
		UP = new Direction(0, -1),
		DOWN = new Direction(0, 1),
		LEFT = new Direction(-1, 0),
		RIGHT = new Direction(1, 0);

	public final static Direction[] DIRECTIONS = {LEFT, RIGHT, UP, DOWN};
	
	public final static String directionChars = "lrud";

	private static int[] directionVectors = {
			-1, 0,
			1, 0,
			0, -1,
			0, 1
	};

	public Direction opposite() {
		return valueOf(-dx, -dy);
	}
	
	public char toChar() {
		return toChar(dx, dy);
	}

	public static char toChar(int dx, int dy) {
		for (int i  = 0; i < directionVectors.length; i += 2) {
			if (dx == directionVectors[i] && dy == directionVectors[i + 1]) {
				return directionChars.charAt(i / 2);
			}
		}
		throw new IllegalArgumentException(dx + "," + dy + " is an illegal direction vector");
	}
	
	public static Direction valueOf(int dx, int dy) {
		return valueOf(toChar(dx, dy));
	}

	public static Direction valueOf(char c) {
		int pos = directionChars.indexOf(Character.toLowerCase(c));
		if (pos < 0) {
			throw new IllegalArgumentException(c + " is an illegal direction character");
		}
		return DIRECTIONS[pos];
	}
}
