package games.battleship.battleship2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Battleship implements IBattleship {

	private int size;
	private List<Cell> board;

	private Collection<ShipType> shipTypes = new ArrayList<ShipType>();
	private Collection<Ship> ships = new ArrayList<Ship>();
	
	public Battleship(ShipType... shipTypes) {
		this.shipTypes.addAll(Arrays.asList(shipTypes));
	}
	
	public void addShipType(ShipType shipType) {
		if (! shipTypes.contains(shipType)) {
			shipTypes.add(shipType);
		}
	}
	
	void init(String hits, Ship... ships) {
		board = new ArrayList<Cell>();
		size = (int) Math.sqrt(hits.length());
		for (int i = 0; i < hits.length(); i++) {
			char c = hits.charAt(i);
			ShipType shipType = null;
			if (c != IBattleship.CELL_EMPTY) {
				Optional<ShipType> foundShipType = shipTypes.stream().filter(st -> st.getCharacter() == c).findFirst();
				if (! foundShipType.isPresent()) {
					throw new IllegalArgumentException("There is not ship type for the " + c);
				}
				shipType = foundShipType.get();
			}
			board.add(new Cell(shipType != null ? new Ship(shipType, i % size, i / size) : null));
		}
	}

	@Override
	public int getSize() {
		return size;
	}
	
	private Cell getCell(int x, int y) {
		return board.get(y * size + x);
	}

	@Override
	public Ship getCellShip(int x, int y) {
		Cell cell = getCell(x, y);
		return cell.getShip();
	}

	@Override
	public boolean isCellHit(int x, int y) {
		return getCell(x, y).isHit();
	}
	
	public boolean isHit(Ship ship) {
		for (int dx = 0; dx < ship.getShipType().getWidth(); dx++) {
			for (int dy = 0; dy < ship.getShipType().getHeight(); dy++) {
				if (! isCellHit(ship.getX() + dx, ship.getY() + dy)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
    public int countShips(ShipType shipType, Boolean hit) {
		return (int) board.stream().filter(cell -> cell.isShip() && (shipType == null || cell.getShipType() == shipType) && (hit == null || cell.isHit() == hit)).count();
    }

	@Override
	public boolean fire(int x, int y) {
		Cell cell = getCell(x, y);
		cell.setHit(true);
		return cell.isShip();
	}

	@Override
	public void load(InputStream inputStream) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void save(OutputStream outputStream) throws IOException {
		// TODO Auto-generated method stub
	}
}
