package games.minesweeper;

public class MineSweeper implements IMineSweeper {

	private Cell board[][];
	
	@Override
	public void init(int width, int height, int bombCount) {
		board = new Cell[height][];
		int remainingCells = width * height, remainingBombs = bombCount; 
		for (int row = 0; row < height; row++) {
			board[row] = new Cell[width];
			for (int col = 0; col < width; col++) {
				boolean isBomb = Math.random() * remainingCells < remainingBombs;
				board[row][col] = new Cell(isBomb);
				if (isBomb) {
					remainingBombs--;
				}
				remainingCells--;
			}
		}
	}

	@Override
	public int getWidth() {
		return board[0].length;
	}

	@Override
	public int getHeight() {
		return board.length;
	}

	@Override
	public boolean isCell(int x, int y, Boolean bomb, Boolean flagged, Boolean opened) {
		Cell cell = getCell(x, y);
		return cell != null &&
				(bomb == null || cell.isBomb() == bomb) &&
				(flagged == null || cell.isFlagged() == flagged) &&
				(opened == null || cell.isOpened() == opened);
	}
	
	private Cell getCell(int x, int y) {
		return x >= 0 && x < getWidth() && y >= 0 && y < getHeight() ? board[y][x] : null;
	}

	private int count(int x1, int x2, int y1, int y2, int exceptX, int exceptY, Boolean bomb, Boolean flagged, Boolean opened) {
		int count = 0;
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				if (x != exceptX || y != exceptY) {
					if (isCell(x, y, bomb, flagged, opened)) {
						count++;
					}
				}
			}
		}
		return count;
	}

	@Override
	public int getBombCount(int x, int y) {
		return count(x - 1, x + 2, y - 1, y + 2, x, y, true, null, null);
	}

	public int count(Boolean bomb, Boolean flagged, Boolean opened) {
		return count(0, getWidth(), 0, getHeight(), -1, -1, bomb, flagged, opened);
	}

	@Override
	public void toggleFlag(int x, int y) {
		Cell cell = getCell(x, y);
		cell.setFlagged(! cell.isFlagged());
	}

	@Override
	public void open(int x, int y) {
		Cell cell = getCell(x, y);
		cell.setOpened(true);
	}
}
