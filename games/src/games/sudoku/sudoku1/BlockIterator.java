package games.sudoku.sudoku1;

import java.util.Iterator;

public class BlockIterator implements Iterator<Cell> {
	
	private final Sudoku sudoku;
	private final int rowOffset, colOffset;
	private int row = 0, col = 0;
	
	public BlockIterator(Sudoku sudoku, int block) {
		this.sudoku = sudoku;
		rowOffset = Math.floorDiv(block, ISudoku.BLOCK_SIZE) * 3;
		colOffset = (block % (ISudoku.BLOCK_SIZE)) * 3;
	}

	@Override
	public boolean hasNext() {
		return row < ISudoku.BLOCK_SIZE;
	}

	@Override
	public Cell next() {
		Cell next = sudoku.getCell(col + colOffset, row + rowOffset);
		col++;
		if (col >= ISudoku.BLOCK_SIZE){
			col = col % ISudoku.BLOCK_SIZE;
			row++;
		}
		return next;
	}
}
