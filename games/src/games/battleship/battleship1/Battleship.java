package games.battleship.battleship1;

import java.util.ArrayList;
import java.util.List;

public class Battleship implements IBattleship {

	private int size;
	private List<Cell> board;

	private Cell getCell(int x, int y) {
		return board.get(y * size + x);
	}

	@Override
	public void init(String level) {
		board = new ArrayList<Cell>();
		size = (int) Math.sqrt(level.length());
		for (int i = 0; i < level.length(); i++) {
			board.add(new Cell(level.charAt(i)));
		}
	}

	@Override
	public int getSize() {
		return size;
	}
	
	@Override
	public char getCellCharacter(int x, int y) {
		Cell cell = getCell(x, y);
		return cell.isHit() ? cell.getCharacter() : IBattleship.CELL_OCEAN;
	}

	@Override
	public boolean isCellHit(int x, int y) {
		return getCell(x, y).isHit();
	}

	@Override
    public int countShips(Boolean hit) {
		return (int) board.stream().filter(cell -> cell.isShip() && (hit == null || cell.isHit() == hit)).count();
    }

	@Override
	public boolean fire(int x, int y) {
		Cell cell = getCell(x, y);
		cell.setHit(true);
		return (cell.getCharacter() != IBattleship.CELL_EMPTY);
	}
}
