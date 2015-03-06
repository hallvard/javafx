package games.battleship.battleship2;

public class Ship {

	private final ShipType shipType;
	private final int x, y;

	public Ship(ShipType shipType, int x, int y) {
		this.shipType = shipType;
		this.x = x;
		this.y = y;
	}

	public ShipType getShipType() {
		return shipType;
	}
	
	public char getCharacter() {
		return shipType.getCharacter();
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isAt(int x, int y) {
		return x >= this.x && x < this.x + shipType.getWidth() && y >= this.y && y < this.y + shipType.getHeight();
	}
}
