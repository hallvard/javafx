package games.tictactoe.tictactoe3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultTicTacToePersistance implements ITicTacToePersistance {
	
	private static final String SEPARATOR = ",";
	
	@Override
	public void load(ITicTacToe ticTacToe, InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		List<Move> moves = bufferedReader.lines().map(line -> {
			String[] parts = line.split(SEPARATOR);
			return new Move(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2].charAt(0));
		}).collect(Collectors.toList());
		ticTacToe.init(moves);
	}

	@Override
	public void save(ITicTacToe ticTacToe, OutputStream outputStream) throws IOException {
		PrintWriter writer = new PrintWriter(outputStream);
		for(Move move : ticTacToe.getMoves()) {
			writer.println(move.x + SEPARATOR + move.y + SEPARATOR + move.player);
		}
		writer.flush();
	}

}
