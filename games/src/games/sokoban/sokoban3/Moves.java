package games.sokoban.sokoban3;

public class Moves implements IMoves {
	
	private final CharSequence moves;
	
	public Moves(CharSequence moves) {
		this.moves = moves;
	}

	@Override
	public CharSequence getMoves() {
		return moves;
	}
}
