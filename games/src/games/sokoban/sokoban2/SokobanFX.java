package games.sokoban.sokoban2;

import games.IUpdateable;
import games.PersistableController;
import games.UndoableController;
import games.imagegrid.ImageGridGame;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SokobanFX extends ImageGridGame<String> implements IUpdateable {

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
		undoableController.setUpdate(this);
		persistableController.setStateStore(sokoban);
		persistableController.setUpdate(this);
		
		persistableController.loadStateFromURL(SokobanFX.class.getResource("/games/sokoban/sample.sok"));
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

	private int playerX, playerY;

	private void updateAroundPlayer() {
		updateCells(playerX - 3, playerY - 3, playerX + 3, playerY + 3);
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
		} else if (code == KeyCode.SPACE) {
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
			updateAroundPlayer();
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
			if (dynamicCellValue == ISokoban.CELL_DYNAMIC_PLAYER) {
				playerX = x;
				playerY = y;
			}
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
}
