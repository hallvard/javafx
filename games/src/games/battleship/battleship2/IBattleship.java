package games.battleship.battleship2;

import games.IPersistable;

public interface IBattleship extends IPersistable {
	
	public static final char CELL_OCEAN = '~';
	public static final char CELL_EMPTY = '.';
	
	/**
	 * Initializes a game with a String representation of a level. All other game state should be cleared.
	 * @param level The level represented as a String.
	 */
	public void init(String level);

	/**
	 * @return The width/height of the grid, as the height and width should be equal.
	 */
	public int getSize();
	
	/**
	 * Get the skip in that cell, or null if the cell is empty.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The ship type.
	 */
	public Ship getCellShip(int x, int y);
	
	/**
	 * Gets the hit state of the cell, i.e. if it has been fired at.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The hit state of the cell.
	 */
	public boolean isCellHit(int x, int y);

	/**
	 * Counts the cells of a ship type that are hit or not.
	 * @param shipType the ship type to count or null to count all ships
	 * @param hit the hit value or null to ignore
	 * @return the ship count
	 */
    public int countShips(ShipType shipType, Boolean hit);
	
	/**
	 * Fires a shot for the current player. 
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return True if the shot hits a ship that has not already been hit, else false.
	 */
	public boolean fire(int x, int y);
}
