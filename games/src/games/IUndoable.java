package games;

public interface IUndoable {

	/**
	 * Tells if the previous action can be undone.
	 * @return if undo can be performed
	 */
	public boolean canUndo();
	
	/**
	 * Undoes a previous move
	 */
	public void undo();

	/**
	 * Tells if there is previously undone action that can be redone.
	 * @return if redo can be performed
	 */
	public boolean canRedo();
	
	/**
	 * Redoes a previously undone
	 */
	public void redo();
}
