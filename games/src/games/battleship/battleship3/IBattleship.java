package games.battleship.battleship3;

import games.imagegrid.ObservableGrid;

import java.util.Collection;
import java.util.List;

public interface IBattleship extends ObservableGrid {

	char CELL_OCEAN = '~';
	char CELL_EMPTY = '.';
	char CELL_SHIP_HIT = 'X';

	/**
	 * Initializes a game with a String representation of a level. All other game state should be cleared.
	 * @param level The level represented as a String.
	 */
	void init(String level);

	/*
     * Initializes a game with a String representation of all the cells' hit state and the ships on the board.
     */
	void init(String hits, List<ShipType> types, List<Ship> ships);

	/**
	 * @return The width/height of the grid, as the height and width should be equal.
	 */
	int getSize();

	/**
	 * @return All ships placed on the board
	 */
	Collection<Ship> getShips();

	/**
	 * @return All ship types on the board
	 */
	Collection<ShipType> getShipTypes();

	/**
	 * Get the ship in that cell, or null if the cell is empty.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The ship type.
	 */
	Ship getCellShip(int x, int y);

	/**
	 * Gets the hit state of the cell, i.e. if it has been fired at.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The hit state of the cell.
	 */
	boolean isCellHit(int x, int y);

	/**
	 * Counts the cells of a ship type that are hit or not.
	 * @param shipType the ship type to count or null to count all ships
	 * @param hit the hit value or null to ignore
	 * @return the ship count
	 */
	int countShips(ShipType shipType, Boolean hit);

	/**
	 * Fires a shot for the current player.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return Null if no ship at coordinate. True if the ship was sunk, false if not.
	 */
	Boolean fire(int x, int y);
}

