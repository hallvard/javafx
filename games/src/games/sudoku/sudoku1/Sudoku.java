package games.sudoku.sudoku1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Sudoku implements ISudoku{

	private List<Cell> cells;

	@Override
	public Integer getInitialValue(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCellValue(int x, int y) {
		return getCell(x, y).getValue();
	}

	@Override
	public boolean isLegalBlock(int block) {
		return isLegalSet(getBlockIterator(block));
	}

	@Override
	public boolean isLegalGrid() {
		return isLegalSet(cells.iterator());
	}
	
	@Override
	public boolean isLegalRow(int row) {
		return isLegalSet(getRowIterator(row));
	}


	@Override
	public boolean isLegalColumn(int col) {
		return isLegalSet(getColumnIterator(col));
	}

	private boolean isLegalSet(Iterator<Cell> iterator) {
		Set<Integer> entries = new HashSet<Integer>();
		while(iterator.hasNext()){
			Cell currentCell = iterator.next();
			if(! currentCell.isEmpty()){
				if(entries.contains(currentCell.getValue()))
					return false;
				else
					entries.add(currentCell.getValue());
			}
		}
		
		return true;
	}
	
	private Iterator<Cell> getRowIterator(int row) {
		return new Iterator<Cell>() {
			int col = 0;
			
			@Override
			public boolean hasNext() {
				return col < ISudoku.BOARD_SIZE;
			}
			
			@Override
			public Cell next() {
				Cell next = getCell(col, row);
				col++;
				return next;
			}
		};
	}
	
	private Iterator<Cell> getColumnIterator(int col) {
		return new Iterator<Cell>() {
			int row = 0;
			
			@Override
			public boolean hasNext() {
				return row < ISudoku.BOARD_SIZE;
			}

			@Override
			public Cell next() {
				Cell next = getCell(col, row);
				row++;
				return next;
			}
		};
	}
	
	private Iterator<Cell> getBlockIterator(int block) {
		return new Iterator<Cell>() {
			int sizeInBlocks = ISudoku.BOARD_SIZE/ISudoku.BLOCK_SIZE;
			
			int rowOffset = Math.floorDiv(block, sizeInBlocks) * 3;
			int colOffset = (block % (sizeInBlocks)) * 3;
			
			int row = 0;
			int col = 0;
			@Override
			public boolean hasNext() {
				return row < ISudoku.BLOCK_SIZE;
			}

			@Override
			public Cell next() {
				Cell next = getCell(col+colOffset, row+rowOffset);
				col++;
				
				if(col >= ISudoku.BLOCK_SIZE){
					col = col % ISudoku.BLOCK_SIZE;
					row++;
				}
				
				return next;
			}
		};
	}


	@Override
	public int countFilledCells(boolean initial) {
		int filled = 0;
		
		for (Cell cell : cells)
			if(!cell.isEmpty())
				filled++;
		
		return filled;
	}

	@Override
	public void init(String level) {
		cells = new ArrayList<Cell>();
	}

	@Override
	public boolean isValidAssignment(int x, int y, int value) {
		return getCell(x, y).isAssignable();
	}

	private Cell getCell(int x, int y) {
		return cells.get(y*ISudoku.BOARD_SIZE + x);
	}


	@Override
	public void placeDigit(int x, int y, int value) {
		if(getCell(x, y).isAssignable())
			getCell(x, y).setValue(value);
		else
			throw new IllegalStateException();
	}

}
