package games.battleship.battleship3;

public interface IEnemy extends IBattleship {

    /**
     * Fires a shot at a coordinate chosen by the enemy artificial intelligence.
     * @return An array of length 2.
     * The second element is the x coordinate.
     * The third element is the y coordinate.
     */
    public GridLocation target();

}
