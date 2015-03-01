package games.battleship;

import java.util.ArrayList;
import java.util.List;

public class Battleship implements IBattleship {

	private int dimension;
	private List<Cell> board;

	private Cell getCell(int x, int y) {
		return board.get(y * dimension + x);
	}

	@Override
	public void init(String level) {
		board = new ArrayList<Cell>();
		dimension = (int) Math.sqrt(level.length());

		for (int i = 0; i < level.length(); i++) {
			board.add(new Cell(level.charAt(i)));
		}
	}

	@Override
	public int getDimension() {
		return dimension;
	}
	
	@Override
	public String getCellString(int x, int y) {
		return getCell(x, y).toString();
	}

	@Override
    public boolean isGameOver() {
        return board.stream().noneMatch(c -> c.getCharacter() == IBattleship.CELL_HIT && !c.hasBeenHit());
    }

	@Override
	public boolean fire(int x, int y) {
		getCell(x, y).setHit(true);
		return (getCell(x, y).getCharacter() == IBattleship.CELL_HIT);
	}
}