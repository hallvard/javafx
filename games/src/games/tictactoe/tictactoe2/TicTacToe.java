package games.tictactoe.tictactoe2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class TicTacToe implements ITicTacToe {
	
	public static final int BOARD_SIZE = 3;
	
	private String gridString;
	private char player;
	
	private Stack<Move> undoStack = new Stack<>();
	private Stack<Move> redoStack = new Stack<>();
	
	private ITicTacToePersistance persistance = new DefaultTicTacToePersistance();
	
	public TicTacToe() {
		init(null);
	}
	
	@Override
	public void init(List<Move> moves) {
		gridString = "         ";
		player = 'x';
		undoStack.clear();
		redoStack.clear();
		if(moves != null) {
			for(Move m : moves) {
				doMove(m);
				undoStack.push(m);
			}
		}
	}

	public char getCell(int x, int y) {
		return gridString.charAt(indexAt(x, y));
	}
	
	private void setCell(char c, int x, int y) {
		int index = indexAt(x, y);
		gridString = gridString.substring(0, index) + c + gridString.substring(index+1);
	}
	
	private boolean isCellEmpty(int x, int y) {
		return getCell(x, y) == ' ';
	}
	
	public char getCurrentPlayer() {
		return player;
	}

	@Override
	public String toString() {
		String result = "-------\n";
		for (int y = 0; y < getHeight(); y++) {
			result += "|";
			for (int x = 0; x < getWidth(); x++) {
				result += gridString.charAt(indexAt(x, y)) + "|";
			}
			result = result + "\n-------\n";
		}
		return result;
	}
	
	/* 
	 * Grid coordinates:
	 * (0,0) | (1,0) | (2,0)
	 * ---------------------
	 * (0,1) | (1,1) | (2,1)
	 * ---------------------
	 * (0,2) | (1,2) | (2,2)
	 */
	public int indexAt(int x, int y) {
		return x + BOARD_SIZE * y;
	}

	private boolean checkNInARow(char c, int n, int x, int y, int dx, int dy) {
		while (n > 0) {
			if (getCell(x, y) != c) {
				return false;
			}
			x += dx;
			y += dy;
			n--;
		}
		return true;
	}
	
	public boolean isWinner(char c) {
		return
			// rows
			checkNInARow(c, 3, 0, 0, 1, 0) || checkNInARow(c, 3, 0, 1, 1, 0) || checkNInARow(c, 3, 0, 2, 1, 0)
			|| // columns
			checkNInARow(c, 3, 0, 0, 0, 1) || checkNInARow(c, 3, 1, 0, 0, 1) || checkNInARow(c, 3, 2, 0, 0, 1)
			|| // diagonals
			checkNInARow(c, 3, 0, 0, 1, 1) || checkNInARow(c, 3, 2, 0, -1, 1)
			;
	}
	
	public boolean isFinished() {
		return getWinner() != null || gridString.indexOf(' ') < 0;
	}

	@Override
	public int getWidth() {
		return (int) Math.sqrt(gridString.length());
	}

	@Override
	public int getHeight() {
		return (int) Math.sqrt(gridString.length());
	}

	private Boolean char2Value(char cell) {
		return cell == '\0' || cell == ' ' ? null : cell == 'x';
	}

	@Override
	public Boolean getCellValue(int x, int y) {
		return char2Value(getCell(x, y));
	}

	@Override
	public int countCells(Boolean value) {
		int count = 0;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if (getCellValue(x, y) == value) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public Boolean getWinner() {
		if (isWinner('x')) {
			return true;
		} else if (isWinner('o')) {
			return false;
		}
		return null;
	}

	@Override
	public Boolean getPlayer() {
		return char2Value(player);
	}

	@Override
	public void placePiece(int x, int y) {
		if(isCellEmpty(x, y)) {
			Move move = new Move(x, y, player);
			doMove(move);
			undoStack.push(move);
			redoStack.clear();
		}
	}
	
	private void doMove(Move move) {
		setCell(move.player, move.x, move.y);
		// Change player
		player = move.player == 'x' ? 'o' : 'x';
	}
	
	private void undoMove(Move move) {
		setCell(' ', move.x, move.y);
		player = move.player;
	}

	@Override
	public boolean canUndo() {
		return !undoStack.isEmpty();
	}

	@Override
	public void undo() {
		if(canUndo()) {
			Move move = undoStack.pop();
			undoMove(move);
			redoStack.push(move);
		}
	}

	@Override
	public boolean canRedo() {
		return !redoStack.isEmpty();
	}

	@Override
	public void redo() {
		if(canRedo()) {
			Move move = redoStack.pop();
			doMove(move);
			undoStack.push(move);
		}
	}

	@Override
	public void load(InputStream inputStream) throws IOException {
		persistance.load(this, inputStream);
	}

	@Override
	public void save(OutputStream outputStream) throws IOException {
		persistance.save(this, outputStream);
	}

	@Override
	public List<Move> getMoves() {
		return new ArrayList<Move>(undoStack);
	}
}
