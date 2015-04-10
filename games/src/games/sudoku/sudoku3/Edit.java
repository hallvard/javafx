package games.sudoku.sudoku3;

public class Edit {

	private Integer x;
	private Integer y;
	private Integer originalValue;
	private Integer newValue;

	
	public Edit(Integer x, Integer y, Integer originalValue, Integer newValue) {
		this.x = x;
		this.y = y;
		this.originalValue = originalValue;
		this.newValue = newValue;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}
	
	public Integer getOriginalValue() {
		return originalValue;
	}

	public Integer getNewValue() {
		return newValue;
	}


	

}
