package games.battleship.battleship2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Example file content:
/*
..X...........X...X......X.......XX.........X........XX.X..X......X.....X..X........................
H32,U13,
H20,U63,
...XXX.....XXXXX.......XX..................X...X...........................X...........X............
H32,U13,
H20,U63,
*/

public class DefaultBattleshipPersistence implements IBattleshipPersistence {

	@Override
	public void load(IBattleshipGame game, InputStream inputStream) throws IOException {

        Scanner scanner = new Scanner(inputStream);

        // First read all lines
        List<String> lines = new ArrayList<>(6);
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();

        // Create IBattleship objects from the lines
        for (int i = 0; i < 2; i++) {

            String hits = lines.get(i * 3);
            System.out.println("hits: " + hits);
            List<String> shipTypeInfo = Arrays.asList(lines.get(1 + i * 3).split(","));
            List<String> shipInfo = Arrays.asList(lines.get(2 + i * 3).split(","));

            List<ShipType> shipTypes = new ArrayList<>();
            shipTypeInfo.forEach(type -> {
                char t = type.charAt(0);
                int width = Character.getNumericValue(type.charAt(1));
                int height = Character.getNumericValue(type.charAt(2));
                shipTypes.add(new ShipType(t, width, height));
            });

            List<Ship> ships = new ArrayList<>();
            shipInfo.forEach(ship -> {
                char type = ship.charAt(0);
                int startRow = Character.getNumericValue(ship.charAt(1));
                int startColumn = Character.getNumericValue(ship.charAt(2));
                ShipType shipType = shipTypes.stream().filter(t -> t.getCharacter() == type).findAny().get();
                ships.add(new Ship(shipType, startColumn, startRow));
            });

            game.getBoards()[i].init(hits, shipTypes, ships);
        }
    }

	@Override
	public void save(IBattleshipGame game, OutputStream outputStream) throws IOException {

        if (game == null || game.getBoards()[0] == null || game.getBoards()[1] == null) {
            return;
        }

		PrintWriter writer = new PrintWriter(outputStream);

        for (IBattleship board : game.getBoards()) {

            // First line is the hits string
            StringBuilder hits = new StringBuilder();

            for (int x = 0; x < board.getSize(); x++) {
                for (int y = 0; y < board.getSize(); y++) {
                    hits.append(board.isCellHit(x, y) ? 'X' : '.');
                }
            }

            writer.println(hits.toString());

            // Second line contains ship types (character, width and height) separated by commas
            StringBuilder typeSB = new StringBuilder();
            board.getShipTypes().forEach(type -> {
                char c = type.getCharacter();
                int width = type.getWidth();
                int height = type.getHeight();
                typeSB.append(c).append(width).append(height).append(",");
            });

            writer.println(typeSB.toString());

            // Third line is the string of ships (character of ship type, x and y) separated by commas
            StringBuilder shipsSB = new StringBuilder();
            board.getShips().forEach(ship -> {
                char type = ship.getCharacter();
                int x = ship.getX();
                int y = ship.getY();
                shipsSB.append(type).append(x).append(y).append(",");
            });

            writer.println(shipsSB.toString());
        }

        writer.flush();
	}

}
