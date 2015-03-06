package games.sokoban.sokoban2;

public class Move {
	
	final int dx, dy;
	final boolean isPush;

	public Move(int dx, int dy, boolean isPush) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.isPush = isPush;
	}

	public Move(char c) {
		int pos = directionChars.indexOf(Character.toLowerCase(c));
		if (pos < 0) {
			throw new IllegalArgumentException(c + " is an illegal direction character");
		}
		this.dx = directionVectors[pos * 2];
		this.dy = directionVectors[pos * 2 + 1];
		this.isPush = Character.isUpperCase(c);
	}
	
	public final static String directionChars = "lrud";

	private static int[] directionVectors = {
			-1, 0,
			1, 0,
			0, -1,
			0, 1
	};

	public static char directionChar(int dx, int dy) {
		for (int i  = 0; i < directionVectors.length; i += 2) {
			if (dx == directionVectors[i] && dy == directionVectors[i + 1]) {
				return directionChars.charAt(i / 2);
			}
		}
		throw new IllegalArgumentException(dx + "," + dy + " is an illegal direction vector");
	}
	
	@Override
	public String toString() {
		char direction = directionChar(dx, dy);
		if (isPush) {
			direction = Character.toUpperCase(direction);
		}
		return String.valueOf(direction);
	}
}
