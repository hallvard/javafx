package games.minesweeper;

public interface IMineSweeper {

	/**
	 * @return The width of the grid
	 */
	public int getWidth();

	/**
	 * @return The height of the grid
	 */
	public int getHeight();
	
	/**
	 * Gets if the cell has the specific values
	 * @param bomb true or false, or null to ignore
	 * @param flagged true or false, or null to ignore
	 * @param opened true or false, or null to ignore
	 * @return if the cell matching the specific values
	 */
	public boolean isCell(int x, int y, Boolean bomb, Boolean flagged, Boolean opened);

	/**
	 * Gets the number of bombs in neighboring cells
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return the number of bombs
	 */
	public int getBombCount(int x, int y);
	
	/**
	 * Toggles the flags indicating that the user things the cell is a bomb
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 */
	public void toggleFlag(int x, int y);

	/**
	 * Opens the cell
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 */
	public void open(int x, int y);

	/**
	 * Counts cells with the specific values
	 * @param bomb true or false, or null to ignore
	 * @param flagged true or false, or null to ignore
	 * @param opened true or false, or null to ignore
	 * @return the number of cells matching the specific values
	 */
	public int count(Boolean bomb, Boolean flagged, Boolean opened);

	/**
	 * (Re)starts the game, with specific dimensions and bomb count
	 * @param width the grid width
	 * @param height the grid height
	 * @param bombCount the total number of bombs (to be placed at random locations)
	 */
	public void init(int width, int height, int bombCount);
}
