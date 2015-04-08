package games.sokoban.sokoban3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISokobanPersistance {

	/**
	 * Initializes the ISokoban instance with the content of the provided InputStream. All other game state should be cleared.
	 * @param sokoban the ISokoban instance
	 * @param inputStream The stream to read the state from, e.g. the standard representation
	 * with lines separated by newlines or vertical bars.
	 * @throws IOException
	 */
	public void load(ISokoban sokoban, InputStream inputStream) throws IOException;

	/**
	 * Saves the state of the ISokoban instance to the provided OutputStream, so it can be restored with load
	 * @param sokoban the ISokoban instance
	 * @param outputStream The stream to write the state to
	 * @throws IOException
	 */
	public void save(ISokoban sokoban, OutputStream outputStream) throws IOException;
}
