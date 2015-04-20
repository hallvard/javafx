package games.battleship.battleship2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Battleship implements IBattleship {

	private int size;
	private List<Cell> board;

	private Collection<ShipType> shipTypes = new ArrayList<>();
	private Collection<Ship> ships = new ArrayList<>();
	
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
			addShipTypes(shipType);
			addShip(ship);
		}
	}

    public void init(String hits, List<ShipType> types, List<Ship> ships) {

        board = new ArrayList<>();
        size = (int) Math.sqrt(hits.length());
        this.ships = ships;
        this.shipTypes = types;

        // Fill board list with empty cells
        for (int i = 0; i < hits.length(); i++) {
            board.add(new Cell(null));
        }

        // Place ships
        for (Ship ship : ships) {
            int x = ship.getX();
            int y = ship.getY();
            int width = ship.getShipType().getWidth();
            int height = ship.getShipType().getHeight();

            for (int row = y; row < y + height; row++) {
                for (int col = x; col < x + width; col++) {
                    Cell cell = new Cell(ship);
                    cell.setHit(hits.charAt(row * size + col) == 'X');
                    board.set(row * size + col, cell);
                }
            }
        }
    }

    public Battleship(ShipType... shipTypes) {
		this.shipTypes.addAll(Arrays.asList(shipTypes));
	}
	
	public void addShipTypes(ShipType... types) {
        for (ShipType shipType : types) {
            if (shipTypes.stream().noneMatch(st -> st.equals(shipType))) {
                shipTypes.add(shipType);
            }
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
	
    @Override
    public Collection<Ship> getShips() {
        return ships;
    }

    @Override
    public Collection<ShipType> getShipTypes() {
        return shipTypes;
    }

    @Override
    public Ship getCellShip(int x, int y) {
        return board.get(y * size + x).getShip();
    }

    @Override
	public int getSize() {
		return size;
	}

	private Cell getCell(int x, int y) {
		return board.get(y * size + x);
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


}
