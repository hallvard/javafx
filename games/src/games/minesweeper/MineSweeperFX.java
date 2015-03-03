package games.minesweeper;

import games.imagegrid.ImageGridGame;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MineSweeperFX extends ImageGridGame<String> {

	@FXML
	private Slider widthSlider, heightSlider, bombCountSlider;

	@FXML
	private Text messageText;

	private Color[] numberColors = {
			Color.WHITE, Color.BLUE, Color.GREEN, Color.RED, Color.ORANGE, Color.YELLOW, Color.BROWN, Color.BROWN, Color.BLACK
	};
	
	@FXML
	protected void initialize() {
		super.initialize();
		imageGrid.setCellColors(1, "grey");
		Font font = Font.font("Arial", 18);
		for (int num = 0; num < numberColors.length; num++) {
			String numString = String.valueOf(num);
			Color color = numberColors[num];
			imageGrid.setImage(numString, createTextImage(numString, font, color, color));
		}
		imageGrid.setImage("flagged", createTextImage("\u2690", font, Color.BLACK, Color.WHITE));
		imageGrid.setImage("mine", createTextImage("\u1F4A3", font, Color.BLACK, Color.BLACK));
	}

	@FXML
	private IMineSweeper mineSweeper;
	
	@FXML
	private void startGame() {
		fillGrid(null);
		mineSweeper.init((int) widthSlider.getValue(), (int) heightSlider.getValue(), (int) bombCountSlider.getValue());
		imageGrid.setRowCount(mineSweeper.getHeight());
		imageGrid.setColumnCount(mineSweeper.getWidth());
		for (int y = 0; y < mineSweeper.getHeight(); y++) {
			for (int x = 0; x < mineSweeper.getWidth(); x++) {
				updateCell(x, y);
			}
		}
		updateStatus();
		ensureKeyboardFocus();
	}

	private boolean isFlagClick = false;
	
	@FXML
	protected void mouseClicked(MouseEvent mouseEvent) {
		isFlagClick = mouseEvent.getButton() == MouseButton.SECONDARY;
		super.mouseClicked(mouseEvent);
	}

	@Override
	protected boolean mouseClicked(int x, int y) {
		if (mineSweeper.count(true, false, true) == 0 && (! mineSweeper.isCell(x, y, null, null, true))) {
			if (isFlagClick) {
				mineSweeper.toggleFlag(x, y);
			} else if (mineSweeper.isCell(x, y, true, null, null)) {
				openAll();
			} else {
				openAllAroundNoBombs(x, y);				
			}
			updateCell(x, y);
			updateStatus();
		}
		return true;
	}

	private void openAll() {
		for (int x = 0; x < mineSweeper.getWidth(); x++) {
			for (int y = 0; y < mineSweeper.getHeight(); y++) {
				mineSweeper.open(x, y);
				updateCell(x, y);
			}
		}
	}

	private int openAllAroundNoBombs(int x, int y) {
		int count = 0;
		if (x < 0 || x >= mineSweeper.getWidth() || y < 0 || y >= mineSweeper.getHeight()) {
			return count;
		}
		if (mineSweeper.isCell(x, y, null, null, true)) {
			return count;
		}
		count++;
		mineSweeper.open(x, y);
		updateCell(x, y);
		if (mineSweeper.getBombCount(x, y) == 0) {
			for (int dx = -1; dx <= 1; dx++) {
				for (int dy = -1; dy <= 1; dy++) {
					count += openAllAroundNoBombs(x + dx, y + dy);
				}
			}
		}
		return count;
	}

	private void updateStatus() {
		int bombCount = mineSweeper.count(true, null, null);
		int flaggedCount = mineSweeper.count(null, true, null);
		int unopenedCount = mineSweeper.count(null, null, false);
		int remaining = unopenedCount - flaggedCount;
		messageText.setText("Bombs: " + bombCount + ", flagged: " + flaggedCount + ", remaining: " + remaining);
	}

	private boolean debug = false;
	
	private String cell2Key(int x, int y) {
		String key = null;
		if (debug && mineSweeper.isCell(x, y, true, null, null)) {
			key = "mine";
		} else if (mineSweeper.isCell(x, y, null, false, false)) {
			key = "unopened";
		} else if (mineSweeper.isCell(x, y, null, true, false)) {
			key = "flagged";
		} else if (mineSweeper.isCell(x, y, true, null, true)) {
			key = "mine";
		} else {
			key = String.valueOf(mineSweeper.getBombCount(x, y));
		}
		return key;
	}

	private void updateCell(int x, int y) {
		String key = cell2Key(x, y);
		setCell(x, y, key);
	}

	public static void main(String[] args) {
		launch(MineSweeperFX.class);
	}
}
