package games;


public interface Undoable {

	/**
	 * Returns true if it is possible to undo an move, false otherwise.
	 */
	public boolean canUndo();
	
	/**
	 * Returns true if it is possible to redo an action, false otherwise.
	 */
	public boolean canRedo();
	
	/**
	 * Performs and undo of the last applied action.
	 */
	public void undo();
	
	/**
	 * Performs and redo of the last undid action.
	 */
	public void redo();
	
}
