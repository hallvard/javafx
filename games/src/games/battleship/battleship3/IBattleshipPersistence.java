package games.battleship.battleship3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IBattleshipPersistence {

	/**
	 * Initializes the IBattleship instance with the content of the provided InputStream. All other game state should be cleared.
	 * @param battleship the IBattleship instance
	 * @param inputStream The stream to read the state from, e.g. the standard representation
	 * with lines separated by newlines or vertical bars.
	 * @throws java.io.IOException
	 */
	public void load(IBattleshipGame battleship, InputStream inputStream) throws IOException;

	/**
	 * Saves the state of the IBattleship instance to the provided OutputStream, so it can be restored with load
	 * @param battleship the IBattleship instance
	 * @param outputStream The stream to write the state to
	 * @throws java.io.IOException
	 */
	public void save(IBattleshipGame battleship, OutputStream outputStream) throws IOException;
	
}