package games.sudoku.sudoku2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISudokuPersistence {

	/**
	 * Initializes the ISudoku instance with the content of the provided InputStream. All other game state should be cleared.
	 * @param sudoku the ISudoku instance
	 * @param inputStream The stream to read the state from, e.g. the standard representation
	 * with lines separated by newlines or vertical bars.
	 * @throws IOException
	 */
	public void load(ISudoku sudoku, InputStream inputStream) throws IOException;

	/**
	 * Saves the state of the ISudoku instance to the provided OutputStream, so it can be restored with load
	 * @param sudoku the ISudoku instance
	 * @param outputStream The stream to write the state to
	 * @throws IOException
	 */
	public void save(ISudoku sudoku, OutputStream outputStream) throws IOException;
	
}
