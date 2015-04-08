package games.sokoban.sokoban2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

public class DefaultSokobanPersistance implements ISokobanPersistance {
	
	public static int char2StaticCellValue(char c) {
		switch (c) {
		case '#': 						return ISokoban.CELL_STATIC_WALL;
		case '.': case '+': case '*':	return ISokoban.CELL_STATIC_TARGET;
		case ' ': case '@': case '$':	return ISokoban.CELL_STATIC_EMPTY;
		}
		return -1;
	}

	public static int char2DynamicCellValue(char c) {
		switch (c) {
		case '@': case '+': 			return ISokoban.CELL_DYNAMIC_PLAYER;
		case '$': case '*': 			return ISokoban.CELL_DYNAMIC_BOX;
		case '#': case '.': case ' ': 	return ISokoban.CELL_DYNAMIC_EMPTY;
		}
		return -1;
	}

	public static char toChar(int staticCellValue, int dynamicCellValue) {
		switch (staticCellValue | dynamicCellValue) {
		case ISokoban.CELL_STATIC_WALL: 									return '#';
		case ISokoban.CELL_STATIC_EMPTY 	| ISokoban.CELL_DYNAMIC_EMPTY:	return ' ';
		case ISokoban.CELL_STATIC_EMPTY 	| ISokoban.CELL_DYNAMIC_PLAYER: return '@';
		case ISokoban.CELL_STATIC_EMPTY 	| ISokoban.CELL_DYNAMIC_BOX: 	return '$';
		case ISokoban.CELL_STATIC_TARGET 	| ISokoban.CELL_DYNAMIC_EMPTY: 	return '.';
		case ISokoban.CELL_STATIC_TARGET 	| ISokoban.CELL_DYNAMIC_PLAYER: return '+';
		case ISokoban.CELL_STATIC_TARGET 	| ISokoban.CELL_DYNAMIC_BOX: 	return '*';
		}
		return '\0';
	}

	//

	@Override
	public void load(ISokoban sokoban, InputStream inputStream) throws IOException {
		Collection<int[]> lines = null;
		String moves = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (line.length() == 0) {
				break;
			}
			if (line.startsWith(";")) {
				continue;
			}
			String[] split = line.split("[\n\\|]+");
			outer: for (int row = 0; row < split.length; row++) {
				int columns = split[row].length();
				int[] values = new int[columns * 2];
				for (int col = 0; col < columns; col++) {
					char c = split[row].charAt(col);
					int s = char2StaticCellValue(c), d = char2DynamicCellValue(c);
					if (s < 0 || d < 0) {
						if (col == 0 && lines != null) {
							moves = line;
							break outer;
						} else {
							throw new IllegalArgumentException("Illegal character: " + c);
						}
					}
					values[col * 2] = s;
					values[col * 2 + 1] = d;
				}
				if (lines == null) {
					lines = new ArrayList<int[]>();
				}
				lines.add(values);
			}
		}
		reader.close();
		sokoban.init(lines.toArray(new int[lines.size()][]), moves);
	}
	
	@Override
	public void save(ISokoban sokoban, OutputStream outputStream) throws IOException {
		PrintWriter writer = new PrintWriter(outputStream);
		writer.print(sokoban);
		writer.flush();
	}
}
