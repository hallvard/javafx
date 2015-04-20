package games.battleship.battleship2;

import games.IPersistable;

public interface IBattleshipGame extends IPersistable {

    /*
     * Initialize the game with the two given boards.
     */
    public void init(IBattleship board1, IBattleship board2);

    /*
     * Returns the two boards belonging to the game.
     * If the game has not been initialized yet, it should be initialized with empty boards and return these.
     */
    public IBattleship[] getBoards();

}
