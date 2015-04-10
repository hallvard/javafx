package games.battleship.battleship3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultBattleshipPersistence implements IBattleshipPersistence {

	@Override
	public void load(IBattleship battleship, InputStream inputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String hits = reader.readLine();

		List<ShipType> shipTypes = new ArrayList<ShipType>();
		List<Ship> ships = (List<Ship>) reader.lines().map(line -> {
            int startRow = Character.getNumericValue(line.charAt(0));
            int startColumn = Character.getNumericValue(line.charAt(1));
            int endRow = Character.getNumericValue(line.charAt(2));
            int endColumn = Character.getNumericValue(line.charAt(3));
            int width = endColumn - startColumn;
            int height = endRow - startRow;

            Optional<ShipType> shipType = shipTypes.stream().filter(st -> st.getHeight() == height && st.getWidth() == width).findAny();
            if (! shipType.isPresent()) {
                ShipType s = new ShipType((char) 0, width, height);
                shipTypes.add(s);
                return new Ship(s, width, height);
            } else {
                return new Ship(shipType.get(), width, height);
            }
        })
		.collect(Collectors.toList());

        battleship.init(hits, ships.toArray(new Ship[ships.size()]));
	}

	@Override
	public void save(IBattleship battleship, OutputStream outputStream) throws IOException {

		PrintWriter writer = new PrintWriter(outputStream);

        String hits = "";

        for (int y = 0; y < battleship.getSize(); y++) {
            for (int x = 0; x < battleship.getSize(); x++) {
                hits += (battleship.isCellHit(x, y)) ? 'X' : '.';
            }
        }

        writer.write(hits);

        String level = Integer.toString(battleship.getSize()) + ",";

        battleship.getShips().stream().map(ship -> {
            char type = ship.getCharacter();
            int x = ship.getX();
            int y = ship.getY();
            int width = ship.getShipType().getWidth();
            int height = ship.getShipType().getHeight();
            return "" + type + x + y + width + height;
        }).collect(Collectors.toList());

        writer.write(level);

        writer.flush();
	}

}