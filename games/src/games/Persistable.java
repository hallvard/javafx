package games;

import java.nio.file.Path;

/**
 * Persistable specifies methods for persisting state by file path.
 */
public interface Persistable {
	
	/**
	 * Loads state from the specified path.
	 * @param path The location of the persisted state.
	 */
	public void load(Path path);
	
	/**
	 * Saves state to the specified path.
	 * @param path The target location of the new persisted state.
	 */
	public void save(Path path);

}
