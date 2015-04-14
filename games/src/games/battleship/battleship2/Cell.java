package games.battleship.battleship2;

public class Cell {

	private final Ship ship;
	private boolean hit;

	public Cell(Ship ship) {
		this.ship = ship;
		setHit(false);
	}

	public Ship getShip() {
		return ship;
	}

	public ShipType getShipType() {
		return ship != null ? ship.getShipType() : null;
	}

	public char getCharacter() {
		return ship != null ? ship.getCharacter() : IBattleship.CELL_OCEAN;
	}

	public boolean isShip() {
		return ship != null;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public boolean isHit() {
		return hit;
	}

	public String toString() {
		char c;
        if (isHit())
            c = (isShip()) ? IBattleship.CELL_SHIP_HIT : IBattleship.CELL_EMPTY;
        else
            c = IBattleship.CELL_OCEAN;
        return String.valueOf(c);
	}
}
