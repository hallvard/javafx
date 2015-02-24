package games.minesweeper;

public class Cell {

	private final boolean bomb;
	private boolean flagged, opened;

	public Cell(boolean bomb, boolean flagged, boolean opened) {
		this.bomb = bomb;
		this.flagged = flagged;
		this.opened = opened;
	}
	
	public Cell(boolean bomb) {
		this(bomb, false, false);
	}

	public boolean isBomb() {
		return bomb;
	}
	
	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public void toggleFlag() {
		flagged = ! flagged;
	}
}
