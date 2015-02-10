package games.sudoku.sudoku1;

public class Cell {

	private boolean assignable;
	private Integer value;

	public boolean isAssignable() {
		return assignable;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}

	public boolean isEmpty() {
		return value == null;
	}

}
