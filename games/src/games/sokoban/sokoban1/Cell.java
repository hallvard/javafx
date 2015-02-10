package games.sokoban.sokoban1;



public class Cell {

	public static int char2StaticCellValue(char c) {
		switch (c) {
		case '#': 						return ISokoban.CELL_STATIC_WALL;
		case '.': case '+': case '*':	return ISokoban.CELL_STATIC_TARGET;
		}
		return ISokoban.CELL_STATIC_EMPTY;
	}

	public static int char2DynamicCellValue(char c) {
		switch (c) {
		case '@': case '+': return ISokoban.CELL_DYNAMIC_PLAYER;
		case '$': case '*': return ISokoban.CELL_DYNAMIC_BOX;
		}
		return ISokoban.CELL_DYNAMIC_EMPTY;
	}

	private int staticCellValue, dynamicCellValue;

	public Cell(char c) {
		if ("-_".indexOf(c) >= 0) {
			c = ' ';
		}
		staticCellValue = char2StaticCellValue(c);
		dynamicCellValue = char2DynamicCellValue(c);
	}

	public int getStaticCellValue() {
		return staticCellValue;
	}

	public int getDynamicCellValue() {
		return dynamicCellValue;
	}

	public Cell() {
		this('#');
	}
	
	public boolean isOccupied() {
		return staticCellValue == ISokoban.CELL_STATIC_WALL || dynamicCellValue != ISokoban.CELL_DYNAMIC_EMPTY;
	}
	
	public boolean isTarget() {
		return staticCellValue == ISokoban.CELL_STATIC_TARGET;
	}

	public boolean isPlayer() {
		return dynamicCellValue == ISokoban.CELL_DYNAMIC_PLAYER;
	}

	public boolean isBox() {
		return dynamicCellValue == ISokoban.CELL_DYNAMIC_BOX;
	}
	
	public void moveTo(Cell cell) {
		if (cell.isOccupied()) {
			throw new IllegalStateException("Cannot move to an occupied cell");
		}
		cell.dynamicCellValue = this.dynamicCellValue;
		this.dynamicCellValue = ISokoban.CELL_DYNAMIC_EMPTY;
	}
	
	public char toChar() {
		switch (staticCellValue | dynamicCellValue) {
		case ISokoban.CELL_STATIC_WALL: 									return '#';
		case ISokoban.CELL_STATIC_EMPTY 	| ISokoban.CELL_DYNAMIC_EMPTY:	return ' ';
		case ISokoban.CELL_STATIC_EMPTY 	| ISokoban.CELL_DYNAMIC_PLAYER: return '@';
		case ISokoban.CELL_STATIC_EMPTY 	| ISokoban.CELL_DYNAMIC_BOX: 	return '$';
		case ISokoban.CELL_STATIC_TARGET 	| ISokoban.CELL_DYNAMIC_EMPTY: 	return '.';
		case ISokoban.CELL_STATIC_TARGET 	| ISokoban.CELL_DYNAMIC_PLAYER: return '+';
		case ISokoban.CELL_STATIC_TARGET 	| ISokoban.CELL_DYNAMIC_BOX: 	return '*';
		}
		return ' ';
	}
	
	@Override
	public String toString() {
		return String.valueOf(toChar());
	}
}
