package games.sudoku.sudoku1;

public interface ISudoku {
	

	int BOARD_SIZE = 9;
	int BLOCK_SIZE = 3;

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
	 * Checks if a particular row has a legal assignment of numbers
	 * @param row The row to check
	 * @return true of the row is legal, false if it is not
	 */
	public boolean isLegalRow(int row);

	/**
	 * Checks if a particular column has a legal assignment of numbers
	 * @param column The column to check
	 * @return true of the column is legal, false if it is not
	 */
	public boolean isLegalColumn(int col);
	
	/**
	 * Checks the validity of a particular block
	 * @param block The block to check
	 * @return true of the block is valid, false if it is not
	 */
	public boolean isLegalBlock(int block);
	
	/**
	 * Checks the legality of the whole grid, i.e. all the rows columns and blocks.
	 * This method does not check if the current solution is correct, only the legality so far.
	 * @return true of the grid is legal, false if it is not
	 */
	public boolean isLegalGrid();
	
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
	public boolean isValidAssignment(int x, int y, int value);

	/**
	 * Places a number.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 */
	public void placeDigit(int x, int y, int value);
}
