package games.imagegrid;

/*
 * Listener interface for grid-like data
 */
public interface GridListener {
	/**
	 * Notifies the listener that the grid has changed. The changed region is a rectangle at x,y with dimensions w,h.
	 * @param grid the grid that has changed
	 * @param x the x coordinate of the changed rectangle
	 * @param y the y coordinate of the changed rectangle
	 * @param w the width of the changed rectangle
	 * @param h the height of the changed rectangle
	 */
	public void gridChanged(ObservableGrid grid, int x, int y, int w, int h);
}
