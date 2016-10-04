package games.sokoban.sokoban1;

import games.imagegrid.ImageGridGame;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SokobanFX extends ImageGridGame<String> {

	@FXML
	private TextField levelTextField;

	@FXML
	private Text messageText;
	
	@FXML
	protected void initialize() {
		super.initialize();
	}

	@FXML
	private ISokoban sokoban;
	
	private int playerX, playerY;
	
	@FXML
	private void startGame() {
		fillGrid(null);
		String level = levelTextField.getText();
		sokoban.init(level);
		imageGrid.setDimensions(sokoban.getWidth(), sokoban.getHeight());
		for (int y = 0; y < sokoban.getHeight(); y++) {
			for (int x = 0; x < sokoban.getWidth(); x++) {
				updateCell(x, y);
			}
		}
		updateStatus();
		ensureKeyboardFocus();
	}

	@Override
	protected boolean keyPressed(int dx, int dy) {
		if (getTargetsLeft() > 0) {
			int x = playerX, y = playerY;
			Boolean push = sokoban.movePlayer(dx, dy);
			if (push != null) {
				updateCell(x, y);
				updateCell(x + dx, y + dy);
				if (push) {
					updateCell(x + dx + dx, y + dy + dy);
				}
			}
			updateStatus();
		}
		return true;
	}

	private void updateStatus() {
		int targetsLeft = getTargetsLeft();
		messageText.setText(targetsLeft == 0 ? "You made it!" : " targets left");
	}

	private int getTargetsLeft() {
		int emptyBoxes = sokoban.countCells(ISokoban.CELL_STATIC_TARGET, ISokoban.CELL_DYNAMIC_EMPTY);
		int playerBoxes = sokoban.countCells(ISokoban.CELL_STATIC_TARGET, ISokoban.CELL_DYNAMIC_PLAYER);
		int targetsLeft = emptyBoxes + playerBoxes;
		return targetsLeft;
	}
	
	private void updateCell(int x, int y) {
		int staticCellValue = sokoban.getStaticCellValue(x, y);
		int dynamicCellValue = sokoban.getDynamicCellValue(x, y);
		if (dynamicCellValue == ISokoban.CELL_DYNAMIC_PLAYER) {
			playerX = x;
			playerY = y;
		}
		String key = cell2key(staticCellValue | dynamicCellValue);
		setCell(x, y, key);
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
}
