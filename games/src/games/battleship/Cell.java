package games.battleship;

public class Cell {

	private char character;
	private boolean hit;

	public Cell(char c) {
		setCharacter(c);
		setHit(false);
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		if (character == IBattleship.CELL_HIT || character == IBattleship.CELL_MISS) 
			this.character = character;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public boolean hasBeenHit() {
		return hit;
	}

	public String toString() {
		if (! hasBeenHit()) return String.valueOf(IBattleship.CELL_OCEAN);
		return String.valueOf(getCharacter());
	}
}
