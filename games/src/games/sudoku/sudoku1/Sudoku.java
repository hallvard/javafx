package games.sudoku.sudoku1;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class Sudoku implements ISudoku{

	private List<Cell> cells;

	@Override
	public void init(String level) {
		cells = new ArrayList<Cell>();
		
		for(int i = 0; i < level.length(); i++){
			char character = level.charAt(i);
			Cell cell = (character == '.' ? new Cell() : new Cell(Character.getNumericValue(character)));
			cells.add(cell);
		}
	}

	// must be package private to support CellIterator
	Cell getCell(int x, int y) {
		return cells.get(y * ISudoku.BOARD_SIZE + x);
	}

	@Override
	public Integer getCellValue(int x, int y) {
		return getCell(x, y).getValue();
	}

	@Override
	public boolean isLegalBlock(int block) {
		return isLegalNumberSet(new BlockIterator(this, block));
	}

	@Override
	public boolean isLegalGrid() {
		for (int i = 0; i < ISudoku.BOARD_SIZE; i++) {
			if (! (isLegalRow(i) && isLegalColumn(i) && isLegalBlock(i))) {
				return false;
			}
		}
//		int maxBlocks = ISudoku.SIZE_IN_BLOCKS * ISudoku.SIZE_IN_BLOCKS;
//		for (int i = 0; i < maxBlocks; i++) {
//			if (! isLegalBlock(i)) {
//				return false;
//			}
//		}
		return true;
	}
	
	@Override
	public boolean isLegalRow(int row) {
		return isLegalNumberSet(new CellIterator(this, 0, row, 1, 0));
	}

	@Override
	public boolean isLegalColumn(int col) {
		return isLegalNumberSet(new CellIterator(this, col, 0, 0, 1));
	}

	private boolean isLegalNumberSet(Iterator<Cell> iterator) {
		BitSet bitSet = new BitSet(BOARD_SIZE);
		while (iterator.hasNext()) {
			Cell currentCell = iterator.next();
			if (! currentCell.isEmpty()) {
				int bitIndex = currentCell.getValue() - 1;
				if (bitSet.get(bitIndex)) {
					return false;
				}
				bitSet.set(bitIndex);
			}
		}
		return true;
	}

	@Override
	public boolean isValidAssignment(int x, int y, Integer value) {
		return getCell(x, y).isAssignable();
	}

	@Override
	public void placeDigit(int x, int y, Integer value) {
		if (! getCell(x, y).isAssignable()) {
			throw new IllegalStateException();
		}
		getCell(x, y).setValue(value);
	}

	@Override
	public int getBlock(int x, int y) {
		return (Math.floorDiv(y, ISudoku.BLOCK_SIZE)) * ISudoku.BLOCK_SIZE + Math.floorDiv(x, ISudoku.BLOCK_SIZE); 
	}

	@Override
	public boolean isAssignable(int x, int y) {
		return getCell(x, y).isAssignable();
	}

	@Override
	public boolean isSolved() {
		return (isLegalGrid() && countEmptyCells() == 0);
	}

	private int countEmptyCells() {
		int count = 0;
		for (Cell cell : this.cells) {
			if (cell.isEmpty()) {
				count++;
			}
		}
		return count;
	}
}
