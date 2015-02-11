package games.sudoku.sudoku1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Sudoku implements ISudoku{

	private List<Cell> cells;

	@Override
	public void init(String level) {
		cells = new ArrayList<Cell>();
		
		for(int i = 0; i < level.length(); i++){
			char character = level.charAt(i);
			if(character == '.'){
				cells.add(new Cell());
			}
			else{
				cells.add(new Cell(Character.getNumericValue(character)));
			}
		}
	}
	
	@Override
	public Integer getCellValue(int x, int y) {
		return getCell(x, y).getValue();
	}

	@Override
	public boolean isLegalBlock(int block) {
		return isLegalNumberSet(getBlockIterator(block));
	}

	@Override
	public boolean isLegalGrid() {
		boolean legal = true;
		
		for(int i = 0; i < ISudoku.BOARD_SIZE; i++){
			legal = legal && isLegalRow(i) && isLegalColumn(i);
		}
		
		int maxBlocks = ISudoku.SIZE_IN_BLOCKS*ISudoku.SIZE_IN_BLOCKS;
		for(int i = 0; i < maxBlocks; i++){
			legal = legal && isLegalBlock(i);
		}
		
		return legal;
	}
	
	@Override
	public boolean isLegalRow(int row) {
		return isLegalNumberSet(getRowIterator(row));
	}


	@Override
	public boolean isLegalColumn(int col) {
		return isLegalNumberSet(getColumnIterator(col));
	}

	private boolean isLegalNumberSet(Iterator<Cell> iterator) {
		Set<Integer> entries = new HashSet<Integer>();
		while(iterator.hasNext()){
			Cell currentCell = iterator.next();
			if(! currentCell.isEmpty()){
				if(entries.contains(currentCell.getValue())){
					return false;
				}
				else{
					entries.add(currentCell.getValue());
				}
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
			int rowOffset = Math.floorDiv(block, ISudoku.SIZE_IN_BLOCKS) * 3;
			int colOffset = (block % (ISudoku.SIZE_IN_BLOCKS)) * 3;
			
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
	public boolean isValidAssignment(int x, int y, Integer value) {
		return getCell(x, y).isAssignable();
	}

	private Cell getCell(int x, int y) {
		return cells.get(y*ISudoku.BOARD_SIZE + x);
	}


	@Override
	public void placeDigit(int x, int y, Integer value) {
		if(getCell(x, y).isAssignable())
			getCell(x, y).setValue(value);
		else
			throw new IllegalStateException();
	}

	@Override
	public int getBlock(int x, int y) {
		return (Math.floorDiv(y, ISudoku.BLOCK_SIZE)) * ISudoku.SIZE_IN_BLOCKS + Math.floorDiv(x, ISudoku.BLOCK_SIZE); 
	}

	@Override
	public boolean isAssignable(int x, int y) {
		return getCell(x, y).isAssignable();
	}

	@Override
	public boolean isSolved() {
		if(isLegalGrid() && countEmptyCells() == 0){
			return true;
		}
		
		return false;
	}

	private int countEmptyCells() {
		int count = 0;
		for (Cell cell : this.cells) {
			if(cell.isEmpty()){
				count++;
			}
		}
		
		return count;
	}

}
