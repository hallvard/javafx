package games;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IPersistable {

	/**
	 * Initializes a game with the content of the provided InputStream. All other game state should be cleared.
	 * @param inputStream The stream to read the state from, e.g. the standard representation
	 * with lines separated by newlines or vertical bars.
	 * @throws IOException
	 */
	public void load(InputStream inputStream) throws IOException;

	/**
	 * Saves a game to the provided OutputStream, so it can be restored with load
	 * @param outputStream The stream to write the state to
	 * @throws IOException
	 */
	public void save(OutputStream outputStream) throws IOException;
}
