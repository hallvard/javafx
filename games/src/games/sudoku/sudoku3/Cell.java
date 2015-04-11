package games.sudoku.sudoku3;

public class Cell {

	private boolean assignable;
	private Integer value;

	public Cell(Integer value) {
		this.value = value;
		this.assignable = false;
	}

	public Cell() {
		this.value = null;
		this.assignable = true;
	}

	public boolean isAssignable() {
		return this.assignable;
	}

	public void setValue(Integer value) {
		
		if(value != null && (value < 1 || value > 9)){
			throw new IllegalArgumentException();
		}
		
		if(this.assignable){
			this.value = value;
		} else{
			throw new IllegalStateException();
		}
	}

	public Integer getValue() {
		return this.value;
	}

	public boolean isEmpty() {
		return this.value == null;
	}

}
