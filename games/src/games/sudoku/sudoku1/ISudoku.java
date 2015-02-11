package games.sudoku.sudoku1;

public interface ISudoku {

	int BOARD_SIZE = 9;
	int BLOCK_SIZE = (int) Math.sqrt(BOARD_SIZE);
	
	/**
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The content of the cell, null for empty
	 */
	public Integer getCellValue(int x, int y);

	/**
	 * Returns which block the specified cell belongs to, given by a flat index.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 */
	public int getBlock(int x, int y);
	
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
	 * Initializes a game with a String representation of a level. All other game state should be cleared.
	 * @param level The level represented as a String.
	 */
	public void init(String level);

	/**
	 * Checks validity of assigning any value in the specified cell.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return
	 */
	public boolean isAssignable(int x, int y);
	
	/**
	 * Checks the validity of placing a certain value in the specified cell
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @param value The value to check
	 * @return true of the value is valid to place at position x, y
	 */
	public boolean isValidAssignment(int x, int y, Integer value);

	/**
	 * Places a number or clears the cell.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @param value The new value, null to clear cell
	 */
	public void placeDigit(int x, int y, Integer value);

	/**
	 * Checks whether the current assignment of values is a successful solution of the puzzle.
	 */
	public boolean isSolved();



	
}
