package games.tictactoe.tictactoe3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ITicTacToePersistance {
	
	/**
	 * Initializes the ITicTacToe instance with the content of the provided InputStream. All other game state should be cleared.
	 * @param ticTacToe the ITicTacToe instance
	 * @param inputStream The stream to read the state from, e.g. the standard representation
	 * with lines separated by newlines or vertical bars.
	 * @throws IOException
	 */
	public void load(ITicTacToe ticTacToe, InputStream inputStream) throws IOException;

	/**
	 * Saves the state of the ITicTacToe instance to the provided OutputStream, so it can be restored with load
	 * @param ticTacToe the ITicTacToe instance
	 * @param outputStream The stream to write the state to
	 * @throws IOException
	 */
	public void save(ITicTacToe ticTacToe, OutputStream outputStream) throws IOException;
	
}
