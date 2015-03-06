package games.battleship.battleship2;

public class ShipType {

	private final char character;
	private final int width, height;

	public ShipType(char character, int width, int height) {
		this.character = character;
		this.width = width;
		this.height = height;
	}

	public char getCharacter() {
		return character;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
