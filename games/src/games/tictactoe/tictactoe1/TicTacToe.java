package games.tictactoe.tictactoe1;


public class TicTacToe implements ITicTacToe {
	
	/*
	 * PART 1
	 */
	
	private String gridString;
	private char player;
	
	public TicTacToe() {
		init(null);
	}
	
	@Override
	public void init(String level) {
		gridString = (level == null ? "         " : level);
		player = 'x';
	}

	public char getCell(int x, int y) {
		return gridString.charAt(indexAt(x, y));
	}
	
	private boolean setCell(char c, int x, int y) {
		if (getCell(x, y) == ' ') {
			int index = indexAt(x, y);
			gridString = gridString.substring(0, index) + c + gridString.substring(index+1);
			return true;
		}
		return false;
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
		return x + 3 * y;
	}

	/*
	 * PART 2
	 */

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
		if (setCell(player, x, y)) {
			// Change player
			player = player == 'x' ? 'o' : 'x';
		}
	}
}
