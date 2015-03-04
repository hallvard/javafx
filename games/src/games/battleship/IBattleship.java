package games.battleship;

public interface IBattleship {
	
	public static final char CELL_OCEAN = '~';
	public static final char CELL_HIT = 'X';
	public static final char CELL_MISS = '.';
	
	/**
	 * Initializes a game with a String representation of a level. All other game state should be cleared.
	 * @param level The level represented as a String.
	 */
	public void init(String level);

	/**
	 * @return The width/height of the grid, as the height and width should be equal.
	 */
	public int getDimension();
	
	/**
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The String representation of a cell. Must be either "~", "." or "X".
	 */
	public String getCellString(int x, int y);
	
	/**
	 * @return Returns true if game is over.
	 */
	public boolean isGameOver();
	
	/**
	 * Fires a shot for the current player. 
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return True if the shot hits a ship that has not already been hit, else false.
	 */
	public boolean fire(int x, int y);
}