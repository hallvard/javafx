package games.sudoku;

public interface ISudoku {
	
	/**
	 * Returns the width/height of a block in this game, which determines the grid width, height and number of blocks.
	 * The standard size is 3, giving a 3x3 block and 9 possible values, but 4 is also common.
	 * @return The size of a block
	 */
	public int getBlockSize();

	/**
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The initial content of the cell
	 */
	public Integer getInitialValue(int x, int y);

	/**
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The content of the cell, null for empty
	 */
	public Integer getCellValue(int x, int y);

	/**
	 * Checks the validity of a particular row
	 * @param row The row to check
	 * @return true of the row is valid, false if it is not
	 */
	public boolean isValidRow(int row);

	/**
	 * Checks the validity of a particular column
	 * @param column The column to check
	 * @return true of the column is valid, false if it is not
	 */
	public boolean isValidColumn(int col);
	
	/**
	 * Checks the validity of a particular block
	 * @param block The block to check
	 * @return true of the block is valid, false if it is not
	 */
	public boolean isValidBlock(int block);
	
	/**
	 * Checks the validity of the whole grid, i.e. all the rows columns and blocks.
	 * This method does not check if the current solution is correct, only the validity so far.
	 * @return true of the grid is valid, false if it is not
	 */
	public boolean isValid();
	
	/**
	 * Counts the filled cells in either the initial or current configuration
	 * @param initial whether or not the count the initial or current configuration
	 * @return The number of filled cells
	 */
	public int countFilledCells(boolean initial);
	
	/**
	 * Initializes a game with a String representation of a level. All other game state should be cleared.
	 * @param level The level represented as a String.
	 */
	public void init(String level);

	/**
	 * Checks the validity of placing a certain value in the specified cell
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @param value The value to check
	 * @return true of the value is valid to place at position x, y
	 */
	public boolean isValid(int x, int y, int value);

	/**
	 * Places a number and returns if it was invalid.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 */
	public void placeDigit(int x, int y, int value);
}
