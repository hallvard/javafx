package games.bubbles;

import games.imagegrid.ImageGridGame;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Bubbles extends ImageGridGame<String> {

	@FXML
	private Label statusLabel;
	
	@FXML
	private List<String> imageNames;

	private int points = 0;
	
	@FXML
	protected void initialize() {
		super.initialize();
		newAction();
	}

	private String getRandomImageName() {
		int imageNum = randomInt(0, imageNames.size() - 1);
		return imageNames.get(imageNum);
	}

	@FXML
	private void newAction() {
		fillGrid(null);
		refillCells();
		points = 0;
		updateStatus();
	}

	private void updateStatus() {
		statusLabel.setText(points + " points. Remaining possibilities: " + countPossibilities());
	}

	@Override
	protected boolean mouseClicked(int x, int y) {
		final int count = burstCells(getCell(x, y), x, y, 1);
		if (count > 0) {
			runDelayed(200,
					() -> {
						collapseCells();
					},
					() -> {
						refillCells();
						points += count * count;
					}
			);
		}
		return true;
	}

	private int burstCells(String value, int x, int y, int limit) {
		if (x < 0 || x >= imageGrid.getColumnCount() || y < 0 || y >= imageGrid.getRowCount()) {
			return 0;
		}
		String cell = getCell(x, y);
		if (cell != null && cell == value) {
			setCell(x, y, null);
			int count =
					burstCells(value, x - 1, y, limit - 1) +
					burstCells(value, x, y - 1, limit - 1) +
					burstCells(value, x + 1, y, limit - 1) +
					burstCells(value, x, y + 1, limit - 1);
			if (count < limit) {
				setCell(x, y, cell);
			}
			return 1 + count;
		}
		return 0;
	}
	
	private void collapseCells() {
		for (int x = 0; x < imageGrid.getColumnCount(); x++) {
			int dropCount = 0;
			for (int y = imageGrid.getRowCount() - 1; y >= 0; y--) {
				String cell = getCell(x, y);
				if (cell == null) {
					dropCount++;
				} else if (dropCount > 0) {
					setCell(x, y + dropCount, cell);
					setCell(x, y, null);
				}
			}
		}
	}

	private void refillCells() {
		foreachCell((x, y) -> {
			if (getCell(x, y) == null) {
				setCell(x, y, getRandomImageName());
			}
		});
	}

	int countPossibilities() {
		return countCells((int x, int y) -> (burstCells(getCell(x, y), x, y, Integer.MAX_VALUE) > 1 ? 1 : 0));
	}

	//
	
	public static void main(String[] args) {
		launch(Bubbles.class);
	}
}
