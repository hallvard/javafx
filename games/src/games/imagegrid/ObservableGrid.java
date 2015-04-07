package games.imagegrid;

public interface ObservableGrid {

	/**
	 * Adds the listener, so it will be notified when the grid changes
	 * @param gridListener the listener to add
	 */
	public void addGridListener(GridListener gridListener);

	/**
	 * Removes the listener, so it no longer will be notified when the grid changes
	 * @param gridListener the listener to remove
	 */
	public void removeGridListener(GridListener gridListener);
}
