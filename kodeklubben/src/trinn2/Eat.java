package trinn2;

import javafx.fxml.FXML;
import javafx.scene.control.Labeled;

public class Eat extends ImageGridGame<Integer> {

	@FXML
	Labeled statusLabel;

	int playerX, playerY;
	double points = 0;
	long startTime = 0;
	
	@FXML
	protected void initialize() {
		super.initialize();
		restartAction();
	}

	void movePlayer(int x, int y) {
		playerX = x;
		playerY = y;
		setCell(playerX, playerY, 0);
	}
	
	@FXML
	void restartAction() {
		fillGrid(-1);
		movePlayer(imageGrid.getColumnCount() / 2, imageGrid.getRowCount() / 2);
		points = 0;
		startTime = System.currentTimeMillis();
		newFood();
		updateGrid();
	}

	void newFood() {
		int x = playerX, y = playerY;
		while (x == playerX && y == playerY) {
			x = randomInt(0, imageGrid.getColumnCount() - 1);
			y = randomInt(0, imageGrid.getRowCount() - 1);
		}
		int value = randomInt(1, 9);
		setCell(x, y, value);
		updateCell(x, y);
	}

	void updateStatus() {
		long millis = System.currentTimeMillis() - startTime;
		double pointsPrMillis = (points * 1000 / millis);
		statusLabel.setText(((double) Math.round(pointsPrMillis * 1000)) / 1000 + " points pr. second (" + points + " points in " + millis / 1000 + " seconds)");
	}

	@Override
	protected boolean keyPressed(int dx, int dy) {
		int nextX = playerX + dx, nextY = playerY + dy;
		if (isValidXY(nextX, nextY)) {
			setCell(playerX, playerY, -1);
			updateCell(playerX, playerY);
			int cell = getCell(nextX, nextY);
			movePlayer(nextX, nextY);
			updateCell(playerX, playerY);
			if (cell > 0) {
				points += cell;
				newFood();
			}
		}
		updateStatus();
		return true;
	}

	//
	
	public static void main(String[] args) {
		launch(args);
	}
}
