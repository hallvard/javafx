package games.tictactoe.tictactoe1;

public interface ITicTacToe {
	
	/**
	 * @return The width of the grid
	 */
	public int getWidth();
	/**
	 * @return The height of the grid
	 */
	public int getHeight();
	
	/**
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The content of the cell, null for empty, true for 'x' and false for 'o'
	 */
	public Boolean getCellValue(int x, int y);

	/**
	 * Counts the cells matching the provided cell value.
	 * @param value. The cell value to match, null for empty, true for 'x' and false for 'o'
	 * @return The number of cells of a particular kind, i.e. matching the provided cell value.
	 */
	public int countCells(Boolean value);
	
	/**
	 * @return The returns the winner, null for none, true for 'x' and false for 'o'
	 */
	public Boolean getWinner();
	
	/**
	 * Initializes a game with a String representation of a level. All other game state should be cleared.
	 * @param level The level represented as a String, e.g. the standard representation
	 * with lines separated by newlines or vertical bars.
	 */
	public void init(String level);
	
	/**
	 * Gets the current player, true for 'x', false for 'o' and null if no game is in progress.
	 * @return the current player.
	 */
	public Boolean getPlayer();
	
	/**
	 * Places a piece for the current player. Afterwards, the other player should be current, or null if game over. 
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 */
	public void placePiece(int x, int y);
}
