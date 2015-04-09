package games.sokoban.sokoban3;

import games.IUpdateable;
import games.PersistableController;
import games.UndoableController;
import games.imagegrid.GridListener;
import games.imagegrid.ImageGridGame;
import games.imagegrid.ObservableGrid;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SokobanFX extends ImageGridGame<String> implements IUpdateable, GridListener {

	@FXML
	private ISokoban sokoban;
	
	@FXML
	private UndoableController undoableController;

	@FXML
	private PersistableController persistableController;

	@Override
	protected void initialize() {
		super.initialize();
		undoableController.setUndoRedo(sokoban);
		persistableController.setStateStore(sokoban);
		persistableController.setUpdate(this);
		
		persistableController.loadStateFromURL(SokobanFX.class.getResource("/games/sokoban/sample.sok"));
		sokoban.addGridListener(this);
	}
	
	@FXML
	private Text messageText;
	
	@Override
	public void updateState(String text) {
		messageText.setText(text);
	}
	
	@Override
	public void updateState(boolean fullUpdate) {
		if (fullUpdate) {
			imageGrid.setDimensions(sokoban.getWidth(), sokoban.getHeight());
			fillGrid(null);
		}
		updateCells(0, 0, sokoban.getWidth(), sokoban.getHeight());
		updateStatus();
	}

	@Override
	protected void keyPressed(KeyEvent keyEvent) {
//		System.out.println(keyEvent);
		super.keyPressed(keyEvent);
		if (keyEvent.isMetaDown()) {
			if (keyEvent.getCode() == KeyCode.X) {
				keyPressed(KeyCode.CUT);
			} else if (keyEvent.getCode() == KeyCode.C) {
				keyPressed(KeyCode.COPY);
			} else if (keyEvent.getCode() == KeyCode.V) {
				keyPressed(KeyCode.PASTE);
			}
		}
	}

	@Override
	protected boolean keyPressed(KeyCode code) {
//		System.out.println(code);
		if (code == KeyCode.BACK_SPACE) {
			undoableController.undo();
		} else if (code == KeyCode.SPACE || code == KeyCode.DELETE) {
			undoableController.redo();
		} else if (code == KeyCode.COPY) {
			persistableController.copyToClipboard();
		} else if (code == KeyCode.PASTE) {
			persistableController.pasteFromClipboard();
		} else {
			return super.keyPressed(code);
		}
		return true;
	}

	@Override
	protected boolean keyPressed(int dx, int dy) {
		if (getTargetsLeft() > 0) {
			sokoban.movePlayer(dx, dy);
			updateStatus();
		}
		return true;
	}

	private void updateStatus() {
		int targetsLeft = getTargetsLeft();
		updateState(targetsLeft == 0 ? "You made it!" : targetsLeft + " targets left");
	}

	private int getTargetsLeft() {
		int emptyBoxes = sokoban.countCells(ISokoban.CELL_STATIC_TARGET, ISokoban.CELL_DYNAMIC_EMPTY);
		int playerBoxes = sokoban.countCells(ISokoban.CELL_STATIC_TARGET, ISokoban.CELL_DYNAMIC_PLAYER);
		int targetsLeft = emptyBoxes + playerBoxes;
		return targetsLeft;
	}
	
	private void updateCells(int x1, int y1, int x2, int y2) {
		for (int y = y1; y < y2; y++) {
			for (int x = x1; x < x2; x++) {
				updateCell(x, y);
			}
		}
	}

	private void updateCell(int x, int y) {
		if (x >= 0 && x < sokoban.getWidth() && y >= 0 && y < sokoban.getHeight()) {
			int staticCellValue = sokoban.getStaticCellValue(x, y);
			int dynamicCellValue = sokoban.getDynamicCellValue(x, y);
			String key = cell2key(staticCellValue | dynamicCellValue);
			setCell(x, y, key);
		}
	}
	
	private String cell2key(int value) {
		switch (value) {
			case ISokoban.CELL_STATIC_WALL: 									return "#";
			case ISokoban.CELL_STATIC_EMPTY 	| ISokoban.CELL_DYNAMIC_EMPTY:	return " ";
			case ISokoban.CELL_STATIC_EMPTY 	| ISokoban.CELL_DYNAMIC_PLAYER: return "@";
			case ISokoban.CELL_STATIC_EMPTY 	| ISokoban.CELL_DYNAMIC_BOX: 	return "$";
			case ISokoban.CELL_STATIC_TARGET 	| ISokoban.CELL_DYNAMIC_EMPTY: 	return ".";
			case ISokoban.CELL_STATIC_TARGET 	| ISokoban.CELL_DYNAMIC_PLAYER: return "+";
			case ISokoban.CELL_STATIC_TARGET 	| ISokoban.CELL_DYNAMIC_BOX: 	return "*";
		}
		return null;
	}

	public static void main(String[] args) {
		launch(SokobanFX.class);
	}

	@FXML
	public void mouseClicked(MouseEvent mouseEvent) {
		super.mouseClicked(mouseEvent);
		if (mouseEvent.getButton() == MouseButton.SECONDARY) {
			persistableController.getClipboardMenu().show(imageGrid, Side.TOP, mouseEvent.getX(), mouseEvent.getX());
		}
		ensureKeyboardFocus();
	}

	private int lastX = -1, lastY = -1;
	
	@Override
	protected boolean mouseClicked(int x, int y) {
		if (lastX < 0 || lastY < 0) {
			return super.mouseDragged(x, y);
		}
		if (sokoban.getStaticCellValue(x, y) == ISokoban.CELL_STATIC_EMPTY && sokoban.getDynamicCellValue(x, y) == ISokoban.CELL_DYNAMIC_EMPTY) {
			sokoban.movePlayerTo(x, y);
		}
		return true;
	}
	
	@Override
	protected Boolean mousePressed(int x, int y) {
		if (sokoban.getDynamicCellValue(x, y) != ISokoban.CELL_DYNAMIC_BOX) {
			return super.mousePressed(x, y);
		}
		lastX = x;
		lastY = y;
		return false;
	}
	
	@Override
	protected boolean mouseDragged(int x, int y) {
		if (lastX < 0 || lastY < 0) {
			return super.mouseDragged(x, y);
		}
		if (x != lastX || y != lastY) {
			String moves = sokoban.moveBox(lastX, lastY, (int) Math.signum(x - lastX), (int) Math.signum(y - lastY));
			if (moves != null) {
				lastX = x;
				lastY = y;
//				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean mouseReleased(int x, int y) {
		lastX = -1;
		lastY = -1;
		return super.mouseReleased(x, y);
	}

	// GridListener

	@Override
	public void gridChanged(ObservableGrid grid, int x, int y, int w, int h) {
		updateCells(x, y, x + w, y + h);
	}
}
