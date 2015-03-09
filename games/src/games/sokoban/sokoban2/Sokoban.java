package games.sokoban.sokoban2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sokoban implements ISokoban {
	
	private int width;
	private List<Cell> grid;

	public Sokoban() {
	}

	@Override
	public void init(int[][] lines, String moves) {
		// first we compute the width, as the maximum length of the lines
		width = 0;
		for (int y = 0; y < lines.length; y++) {
			width = Math.max(width, lines[y].length / 2);
		}
		grid = new ArrayList<Cell>(lines.length * width);
		playerX = playerY = -1;
		// fill the array with the characters in the lines
		// note that lines may be shorter than the width, so some cells may not be set
		for (int y = 0; y < lines.length; y++) {
			int[] line = lines[y];
			for (int x = 0; x < width * 2; x += 2) {
				Cell cell = (x < line.length ? new Cell(line[x], line[x + 1]) : new Cell());
				if (cell.isPlayer()) {
					if (playerX >= 0 && playerY >= 0) {
						throw new IllegalArgumentException("Cannot have more than one player");
					}
					playerX = x / 2;
					playerY = y;
				}
				grid.add(cell);
			}
		}
		if (playerX < 0 && playerY < 0) {
			throw new IllegalArgumentException("Must have a player");
		}
		this.moves = new ArrayList<Move>();
		undoPos = 0;
		if (moves != null) {
			for (int i = 0; i < moves.length(); i++) {
				this.moves.add(new Move(moves.charAt(i)));
			}
			undoPos = moves.length();
		}
	}
	
	@Override
	public String toString() {
		return toString("\n") + getMoves();
	}
	
	public String toString(String separator) {
		// with a StringBuilder, we can create a String without garbage
		StringBuilder builder = new StringBuilder();
		int num = 0;
		// for each cell
		for (Cell cell : grid) {
			// add the corresponding char
			builder.append(DefaultSokobanPersistance.toChar(cell.getStaticCellValue(), cell.getDynamicCellValue()));
			num++;
			// if end-of-line add separator
			if (num == width) {
				builder.append(separator);
				num = 0;
			}
		}
		// convert the StringBuilder to a String
		return builder.toString();
	}

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return grid.size() / width;
	}

	public Cell getCell(int x, int y) {
		if (y < 0 || x < 0 || y >= getHeight() || x >= getWidth()) {
			return null;
		}
		return grid.get(y * width + x);
	}

	@Override
	public int countCells(Integer cellStatic, Integer cellDynamic) {
		int count = 0;
		// iterate over all cells
		for (Cell cell : grid) {
			if ((cellStatic == null || cellStatic.intValue() == cell.getStaticCellValue()) &&
				(cellDynamic == null || cellDynamic.intValue() == cell.getDynamicCellValue())) {
				count++;
			}
		}
		return count;
	}

	private int playerX = -1, playerY = -1;

	private Boolean doMove(int dx, int dy) {
		// a legal move is 1 in one direction and 0 in the other
		int x = playerX, y = playerY;
		boolean isPush = false;
		// if we need to move a box, i.e. overwrite the target, whether goal or floor
		Cell cell = getCell(x, y);
		Cell forward1 = getCell(x + dx, y + dy);
		Cell forward2 = getCell(x + dx + dx, y + dy + dy);
		if (forward1.isBox() && (! forward2.isOccupied())) {
			// remember that the move is a push
			isPush = true;
			forward1.moveTo(forward2);
		}
		if (isPush || (! forward1.isOccupied())) {
			// then move the player, i.e. overwrite the target, whether box (just moved), goal or floor
			cell.moveTo(forward1);
			playerX += dx;
			playerY += dy;
			return isPush;
		}
		return null;
	}

	private void undoMove(int dx, int dy, boolean wasPush) {
		int x = playerX, y = playerY;
		Cell cell = getCell(x, y);
		Cell backward = getCell(x - dx, y - dy);
		cell.moveTo(backward);
		if (wasPush) {
			Cell forward = getCell(x + dx, y + dy);
			forward.moveTo(cell);
		}
		playerX -= dx;
		playerY -= dy;
	}
	
	// ISokoban
	
	@Override
	public int getStaticCellValue(int x, int y) {
		return getCell(x, y).getStaticCellValue();
	}

	@Override
	public int getDynamicCellValue(int x, int y) {
		return getCell(x, y).getDynamicCellValue();
	}

	private ISokobanPersistance sokobanPersistance = new DefaultSokobanPersistance();

	public void setSokobanPersistance(ISokobanPersistance sokobanPersistance) {
		this.sokobanPersistance = sokobanPersistance;
	}
	
	@Override
	public void load(InputStream inputStream) throws IOException {
		sokobanPersistance.load(this, inputStream);
	}
	
	@Override
	public void save(OutputStream outputStream) throws IOException {
		sokobanPersistance.save(this, outputStream);
	}

	//
	
	private List<Move> moves;
	private int undoPos = 0;
	
	public String getMoves() {
		return moves.stream().map(m -> m.toString()).collect(Collectors.joining());
	}
	
	@Override
	public Boolean movePlayer(int dx, int dy) {
		Boolean isPush = doMove(dx, dy);
		if (isPush != null) {
			Move moveCommand = new Move(dx, dy, isPush);
			while (moves.size() > undoPos) {
				moves.remove(moves.size() - 1);
			}
			moves.add(moveCommand);
			undoPos++;
		}
		return isPush;
	}

	@Override
	public boolean canUndo() {
		return undoPos > 0;
	}
	
	@Override
	public void undo() {
		Move moveCommand = null;
		if (canUndo()) {
			undoPos--;
			moveCommand = moves.get(undoPos);
			undoMove(moveCommand.dx, moveCommand.dy, moveCommand.isPush);
		}
	}

	@Override
	public boolean canRedo() {
		return undoPos < moves.size();
	}
	
	@Override
	public void redo() {
		if (canRedo()) {
			Move moveCommand = moves.get(undoPos);
			undoPos++;
			doMove(moveCommand.dx, moveCommand.dy);
		}
	}
}
