package games.battleship.battleship1;

public interface IBattleship {
	
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
	 * Get the character representation of a cell.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The character representation of a cell. Must be {@link CELL_OCEAN}, {@link CELL_EMPTY} or another character that represents a ship (type).
	 */
	public char getCellCharacter(int x, int y);
	
	/**
	 * Gets the hit state of the cell, i.e. if it has been fired at.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The hit state of the cell.
	 */
	public boolean isCellHit(int x, int y);

	/**
	 * Counts the ships that are hit or not.
	 * @param hit the hit value or null to ignore
	 * @return the ship count
	 */
    public int countShips(Boolean hit);
	
	/**
	 * Fires a shot for the current player. 
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return True if the shot hits a ship that has not already been hit, else false.
	 */
	public boolean fire(int x, int y);
}
