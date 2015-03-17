package games.battleship.battleship2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Battleship implements IBattleship {

	private int size;
	private List<Cell> board;

	private Collection<ShipType> shipTypes = new ArrayList<ShipType>();
	private Collection<Ship> ships = new ArrayList<Ship>();
	
	private IBattleshipPersistence battleshipPersistence = new DefaultBattleshipPersistence();

    public Battleship() {}

	public void init(String level) {
		
		String[] lvl = level.split(",");

        // The first element contains the board size
		size = Integer.valueOf(lvl[0]);

        // Fill the board list with empty cells
        board = new ArrayList<>(size*size);
        for (int i = 0; i < size*size; i++) {
            board.add(new Cell(null));
        }

        // Fill ships and shipTypes collections
        for (int i = 1; i < lvl.length; i++) {
            String shipString = lvl[i];
            char type = shipString.charAt(0);
            int startRow = Character.getNumericValue(shipString.charAt(1));
            int startColumn = Character.getNumericValue(shipString.charAt(2));
            int endRow = Character.getNumericValue(shipString.charAt(3));
            int endColumn = Character.getNumericValue(shipString.charAt(4));
            int width = endColumn - startColumn + 1;
            int height = endRow - startRow + 1;

			ShipType shipType = new ShipType(type, width, height);
			Ship ship = new Ship(shipType, startColumn, startRow);
			addShipType(shipType);
			addShip(ship);
		}
	}

	public Battleship(ShipType... shipTypes) {
		this.shipTypes.addAll(Arrays.asList(shipTypes));
	}
	
	public void addShipType(ShipType shipType) {
		if (shipTypes.stream().noneMatch(st -> st.equals(shipType))) {
			shipTypes.add(shipType);
		}
	}

    public void addShip(Ship ship) {
        //TODO: Do not accept collisions (ships on top of each other).
        ships.add(ship);
        for (int row = ship.getY(); row < ship.getY() + ship.getShipType().getHeight(); row++) {
            for (int col = ship.getX(); col < ship.getX() + ship.getShipType().getWidth(); col++) {
                board.set(row * size + col, new Cell(ship));
            }
        }
    }
	
	public void init(String hits, Ship... ships) {
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
    public Collection<Ship> getShips() {
        return ships;
    }

	@Override
	public int getSize() {
		return size;
	}

    @Override
	public Cell getCell(int x, int y) {
		return board.get(y * size + x);
	}

	public Ship getCellShip(int x, int y) {
		Cell cell = getCell(x, y);
		return cell.getShip();
	}

	@Override
	public boolean isCellHit(int x, int y) {
		return getCell(x, y).isHit();
	}
	
	public boolean isSunk(Ship ship) {
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
	public Boolean fire(int x, int y) {
		Cell cell = getCell(x, y);
		cell.setHit(true);
	    Ship ship = cell.getShip();
        return (ship == null) ? null: isSunk(ship);
    }

	@Override
	public void load(InputStream inputStream) throws IOException {
	    battleshipPersistence.load(this, inputStream);
    }

	@Override
	public void save(OutputStream outputStream) throws IOException {
		battleshipPersistence.save(this, outputStream);
	}
}
