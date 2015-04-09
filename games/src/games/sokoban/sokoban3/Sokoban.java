package games.sokoban.sokoban3;

import games.imagegrid.GridListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
		this.moves = new ArrayList<IMoves>();
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

	private Boolean doMove(Direction direction) {
		int dx = direction.dx, dy = direction.dy;
		// a legal move is 1 in one direction and 0 in the other
		final int x = playerX, y = playerY;
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
			fireGridChanged(x, y, dx, dy, isPush);
			return isPush;
		}
		return null;
	}

	private void undoMove(Direction direction, boolean wasPush) {
		int dx = direction.dx, dy = direction.dy;
		final int x = playerX, y = playerY;
		Cell cell = getCell(x, y);
		Cell backward = getCell(x - dx, y - dy);
		cell.moveTo(backward);
		if (wasPush) {
			Cell forward = getCell(x + dx, y + dy);
			forward.moveTo(cell);
		}
		playerX -= dx;
		playerY -= dy;
		fireGridChanged(playerX, playerY, dx, dy, wasPush);
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
	
	private List<IMoves> moves;
	private int undoPos = 0;
	
	public String getMoves() {
		return moves.stream().map(m -> m.toString()).collect(Collectors.joining());
	}
	
	@Override
	public Boolean movePlayer(int dx, int dy) {
		Direction direction = Direction.valueOf(dx, dy);
		Boolean isPush = (direction != null ? doMove(direction) : null);
		if (isPush != null) {
			pushMoves(new Move(direction, isPush));
		}
		return isPush;
	}

	private void pushMoves(IMoves moveCommand) {
		while (moves.size() > undoPos) {
			moves.remove(moves.size() - 1);
		}
		moves.add(moveCommand);
		undoPos++;
	}

	@Override
	public boolean canUndo() {
		return undoPos > 0;
	}
	
	private void doMoves(CharSequence moves, Boolean pushEach) {
		for (int i = 0; i < moves.length(); i++) {
			char c = moves.charAt(i);
			Direction direction = Direction.valueOf(c);
			if (Boolean.TRUE.equals(pushEach)) {
				movePlayer(direction.dx, direction.dy);
			} else {
				doMove(direction);
			}
		}
		if (Boolean.FALSE.equals(pushEach)) {
			pushMoves(new Moves(moves));
		}
	}
	
	@Override
	public void undo() {
		if (canUndo()) {
			undoPos--;
			CharSequence moves = this.moves.get(undoPos).getMoves();
			for (int i = moves.length() - 1; i >= 0; i--) {
				char c = moves.charAt(i);
				undoMove(Direction.valueOf(c), Move.isPush(c));
			}
		}
	}

	@Override
	public boolean canRedo() {
		return undoPos < moves.size();
	}
	
	@Override
	public void redo() {
		if (canRedo()) {
			IMoves moveCommand = moves.get(undoPos);
			undoPos++;
			doMoves(moveCommand.getMoves(), null);
		}
	}

	//
	
	private Collection<GridListener> gridListeners = new ArrayList<GridListener>();
	
	@Override
	public void addGridListener(GridListener gridListener) {
		gridListeners.add(gridListener);
	}

	@Override
	public void removeGridListener(GridListener gridListener) {
		gridListeners.remove(gridListener);
	}
	
	private void fireGridChanged(int x, int y, int dx, int dy, boolean isPush) {
		final int[] xw = (dx > 0 ? new int[]{x, dx} : new int[]{x + dx * (isPush ? 2 : 1), -dx});
		final int[] yh = (dy > 0 ? new int[]{y, dy} : new int[]{y + dy * (isPush ? 2 : 1), -dy});
		fireGridChanged(xw[0], yh[0], xw[1] * (isPush ? 2 : 1) + 1, yh[1] * (isPush ? 2 : 1) + 1);
	}
	
	private void fireGridChanged(int x, int y, int w, int h) {
		for (GridListener gridListener : gridListeners) {
			gridListener.gridChanged(this, x, y, w, h);
		}
	}

	//
	
	private CharSequence computeMovesToGoal(int goalX, int goalY) {
		Cell goalValue = getCell(goalX, goalY);
		if (goalValue.isOccupied()) {
			return null;
		}
		// we extend a boundary, like riples in the water, from the starting point
		Queue<Integer> boundary = new LinkedList<Integer>();
		boundary.add(playerX);
		boundary.add(playerY);
		// as long as there are more cells to consider
		StringBuilder moves = null;
		while (boundary.size() > 0) {
			// remove current position
			int x = boundary.poll(), y = boundary.poll();
			for (Direction direction : Direction.DIRECTIONS) {
				int nx = x + direction.dx, ny = y + direction.dy;
				Cell cell = getCell(nx, ny);
				// if this is a new and unoccupied cell
				if (cell.getDirection() == null && (! cell.isOccupied())) {
					// note the direction we came from
					cell.setDirection(direction);
					// if this is goal, walk backwards (the opposite direction) and collect moves
					if (nx == goalX && ny == goalY) {
						moves = new StringBuilder(getWidth() + getHeight());
						while (nx != playerX || ny != playerY) {
							Cell moveCell = getCell(nx, ny);
							Direction moveDirection = moveCell.getDirection();
							moves.append(Direction.toChar(moveDirection.dx, moveDirection.dy));
							nx -= moveDirection.dx;
							ny -= moveDirection.dy;
						}
						moves.reverse();
						break;
					}
					// enqueue this position, so we can consider it later
					boundary.offer(nx);
					boundary.offer(ny);
				}
			}
		}
		for (Cell cell : grid) {
			cell.setDirection(null);
		}
		return moves;
	}
	
	@Override
	public String movePlayerTo(int x, int y) {
		CharSequence moves = computeMovesToGoal(x, y);
		if (moves == null) {
			return null;
		}
		doMoves(moves, true);
		return moves.toString();
	}
	
	@Override
	public String moveBox(int x, int y, int dx, int dy) {
		Cell box = getCell(x, y);
		Direction direction = Direction.valueOf(dx, dy);
		if ((! box.isBox()) || direction == null) {
			return null;
		}
		Cell target = getCell(x + dx, y + dy);
		if (target == null || (target.isOccupied() && (! target.isPlayer()))) {
			return null;
		}
		Cell player = getCell(x - dx, y - dy);
		if (player == null || (player.isOccupied() && (! player.isPlayer()))) {
			return null;
		}
		CharSequence moves = "";
		if (! player.isPlayer()) {
			moves = movePlayerTo(x - dx, y - dy);
			if (moves == null) {
				return null;
			}
		}
		if (movePlayer(dx, dy) == null) {
			return null;
		}
		return moves.toString() + Move.toPush(direction.toChar());
	}
}
