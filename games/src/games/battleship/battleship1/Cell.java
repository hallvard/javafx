package games.battleship.battleship1;

public class Cell {

	private final char character;
	private boolean hit;

	public Cell(char c) {
		this.character = c;
		setHit(false);
	}

	public char getCharacter() {
		return character;
	}

	public boolean isShip() {
		return character != IBattleship.CELL_OCEAN && character != IBattleship.CELL_EMPTY;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public boolean isHit() {
		return hit;
	}

	public String toString() {
		return String.valueOf(isHit() ? getCharacter() : IBattleship.CELL_OCEAN);
	}
}
