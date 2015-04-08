package games.sokoban.sokoban3;

public class Direction {

	public final int dx, dy;

	private Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
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

	public char directionChar() {
		return directionChar(dx, dy);
	}

	public static char directionChar(int dx, int dy) {
		for (int i  = 0; i < directionVectors.length; i += 2) {
			if (dx == directionVectors[i] && dy == directionVectors[i + 1]) {
				return directionChars.charAt(i / 2);
			}
		}
		throw new IllegalArgumentException(dx + "," + dy + " is an illegal direction vector");
	}
	
	public static Direction directionFor(int dx, int dy) {
		return directionFor(directionChar(dx, dy));
	}

	public static Direction directionFor(char c) {
		int pos = directionChars.indexOf(Character.toLowerCase(c));
		if (pos < 0) {
			throw new IllegalArgumentException(c + " is an illegal direction character");
		}
		return DIRECTIONS[pos];
	}
}
