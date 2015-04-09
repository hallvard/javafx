package games.sokoban.sokoban3;

import games.IPersistable;
import games.IUndoable;
import games.imagegrid.ObservableGrid;

public interface ISokoban extends IUndoable, IPersistable, ObservableGrid {

	public int CELL_STATIC_EMPTY = 0, CELL_STATIC_TARGET = 1, CELL_STATIC_WALL = 2;
	public int CELL_DYNAMIC_EMPTY = 0, CELL_DYNAMIC_PLAYER = 4, CELL_DYNAMIC_BOX = 8;
	
	/**
	 * @return The width of the grid
	 */
	public int getWidth();
	/**
	 * @return The height of the grid
	 */
	public int getHeight();
	
	/**
	 * Gets the static part of the cell, i.e. the part that never changes.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The static content of a cell, either CELL_STATIC_EMPTY, CELL_STATIC_TARGET or CELL_STATIC_WALL
	 */
	public int getStaticCellValue(int x, int y);

	/**
	 * Gets the dynamic part of the cell, i.e. the movable part.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The dynamic content of a cell, either CELL_DYNAMIC_EMPTY, CELL_DYNAMIC_PLAYER or CELL_DYNAMIC_BOX
	 */
	public int getDynamicCellValue(int x, int y);

	/**
	 * Count the number of cells matching a particular combination of static and dynamic cell value.
	 * @param cellStatic The static part to match, or null if it should be ignored when matching.
	 * @param cellDynamic The dynamic part to match, or null if it should be ignored when matching.
	 * @return The number of cells of a particular kind, i.e. matching cellStatic and cellDynamic
	 */
	public int countCells(Integer cellStatic, Integer cellDynamic);

	/**
	 * Gets the moves as a String, with l, r, u, d to indicate the directions, and case to indicate move (lower) and push (upper)
	 * @return
	 */
	public String getMoves();

	/**
	 * Initializes game with the provided data.
	 * @param lines lines of cell values, with two ints pr cell
	 * @param moves moves encoded as for getMoves()
	 */
	public void init(int[][] lines, String moves);

	/**
	 * Moves the player in the indicated direction. Returns whether or not it was a push, or legal at all. 
	 * @param dx
	 * @param dy
	 * @return Returns TRUE if the move was a push, FALSE if it was a move or null of it was illegal. 
	 */
	public Boolean movePlayer(int dx, int dy);
	
	/**
	 * Moves the player to the indicated cell, using a sequence of moves. Returns the sequence of moves as a String (see getMoves).
	 * @param x
	 * @param y
	 * @return Returns the sequence of moves as a String (see getMoves) or null if the move was impossible.
	 */
	public String movePlayerTo(int x, int y);

	/**
	 * Moves the box at the indication position in the indicated direction, using a sequence of moves and a pushes. Returns the sequence of moves and pushse as a String (see getMoves). 
	 * @param dx
	 * @param dy
	 * @return Returns the sequence of moves and pushes as a String (see getMoves) or null if the move was impossible.
	 */
	public String moveBox(int x, int y, int dx, int dy);
}
