package games.sudoku.sudoku2;

import java.util.Iterator;

class CellIterator implements Iterator<Cell> {
	
	private final Sudoku sudoku;
	private int x, y, dx, dy;

	public CellIterator(Sudoku sudoku, int x, int y, int dx, int dy) {
		super();
		this.sudoku = sudoku;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public boolean hasNext() {
		return x < ISudoku.BOARD_SIZE && y < ISudoku.BOARD_SIZE;
	}

	@Override
	public Cell next() {
		Cell next = sudoku.getCell(x, y);
		x += dx;
		y += dy;
		return next;
	}
}
