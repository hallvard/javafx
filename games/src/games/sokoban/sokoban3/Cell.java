package games.sokoban.sokoban3;

public class Cell {

	private int staticCellValue, dynamicCellValue;

	public Cell(int staticCellValue, int dynamicCellValue) {
		this.staticCellValue = staticCellValue;
		this.dynamicCellValue = dynamicCellValue;
	}

	public int getStaticCellValue() {
		return staticCellValue;
	}

	public int getDynamicCellValue() {
		return dynamicCellValue;
	}

	public Cell() {
		this(ISokoban.CELL_STATIC_EMPTY, ISokoban.CELL_DYNAMIC_EMPTY);
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
	
	@Override
	public String toString() {
		return String.valueOf(DefaultSokobanPersistance.toChar(staticCellValue, dynamicCellValue));
	}
	
	//
	
	private Direction direction;
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
