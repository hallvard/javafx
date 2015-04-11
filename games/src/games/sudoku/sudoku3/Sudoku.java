package games.sudoku.sudoku3;

import games.imagegrid.GridListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sudoku implements ISudoku{

	private List<Cell> cells;

	@Override
	public void init(String level, List<Edit> edits) {
		cells = new ArrayList<Cell>();
		
		for(int i = 0; i < level.length(); i++){
			char character = level.charAt(i);
			Cell cell = (character == '.' ? new Cell() : new Cell(Character.getNumericValue(character)));
			cells.add(cell);
		}
		
		for(Edit edit: edits){
			updateCell(edit.getX(), edit.getY(), edit.getNewValue());
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
		edits.push(new Edit(x, y, getCell(x, y).getValue(), value));
		undoneEdits.clear();
		
		updateCell(x, y, value);
	}

	private void updateCell(int x, int y, Integer value) {
		if (! getCell(x, y).isAssignable()) {
			throw new IllegalStateException();
		}
		getCell(x, y).setValue(value);
		
		fireCellChanged(x, y);
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

	private Stack<Edit> edits = new Stack<Edit>();
	private Stack<Edit> undoneEdits = new Stack<Edit>();
	
	@Override
	public boolean canUndo() {
		return edits.size() > 0;
	}

	@Override
	public void undo() {
		Edit editToUndo = edits.pop();
		updateCell(editToUndo.getX(), editToUndo.getY(), editToUndo.getOriginalValue());
		undoneEdits.push(editToUndo);
	}

	@Override
	public boolean canRedo() {
		return undoneEdits.size() > 0;
	}

	@Override
	public void redo() {
		Edit editToRedo = undoneEdits.pop();
		updateCell(editToRedo.getX(), editToRedo.getY(), editToRedo.getNewValue());
		edits.push(editToRedo);
	}

	private ISudokuPersistence sudokuPersistence = new DefaultSudokuPersistance();
	
	
	@Override
	public void load(InputStream inputStream) throws IOException {
		sudokuPersistence.load(this, inputStream);
	}
	
	@Override
	public void save(OutputStream outputStream) throws IOException {
		sudokuPersistence.save(this, outputStream);
	}

	private List<GridListener> listeners = new ArrayList<GridListener>();
	
	@Override
	public void addGridListener(GridListener gridListener) {
		this.listeners.add(gridListener);
	}

	@Override
	public void removeGridListener(GridListener gridListener) {
		this.listeners.remove(gridListener);
	}

	@Override
	public List<Integer> placeableDigits(int x, int y) {
		if(!isAssignable(x, y)){
			return new ArrayList<Integer>();
		}
		
		List<Integer> candidateDigits = IntStream.range(1, ISudoku.BOARD_SIZE + 1).boxed().collect(Collectors.toList()); 
		
		filterNumbers(candidateDigits, new CellIterator(this, x, 0, 0, 1));
		filterNumbers(candidateDigits, new CellIterator(this, 0, y, 1, 0));
		filterNumbers(candidateDigits, new BlockIterator(this, getBlock(x, y)));
		
		return candidateDigits;
	}
	
	private void filterNumbers(List<Integer> candidateDigits, Iterator<Cell> iterator) {
		while(iterator.hasNext()){
			Cell cell = iterator.next();
			if(candidateDigits.contains(cell.getValue())){
				candidateDigits.remove(cell.getValue());
			}
		}
	}

	private void fireCellChanged(int x, int y) {
		for(GridListener listener : this.listeners){
			listener.gridChanged(this, x, y, 1, 1);
		}
	}
}
