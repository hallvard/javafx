package games.eat;

import games.imagegrid.ImageGridGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Eat extends ImageGridGame<Integer> {

	@FXML
	Labeled statusLabel;

	int playerX, playerY;
	int playerDx = 0, playerDy = 0;
	boolean contMove = false;
	double points = 0;
	long startTime = 0;
	
	@FXML
	protected void initialize() {
		super.initialize();
		imageGrid.setCellColors(1, "black");
		for (int num = 0; num < 10; num++) {
			imageGrid.setImage(num, createTextImage(num, Font.font("Arial", 12), Color.WHITE, Color.BLACK));
		}
		restartAction();
	}

	@FXML
	void restartAction() {
		fillGrid(-1);
		playerDx = 0;
		playerDy = 0;
		movePlayerTo(imageGrid.getColumnCount() / 2, imageGrid.getRowCount() / 2);
		points = 0;
		startTime = System.currentTimeMillis();
		newFood();
	}

	private void movePlayerTo(int x, int y) {
		setCell(playerX, playerY, -1);
		playerX = x;
		playerY = y;
		setCell(playerX, playerY, 0);
	}

	@FXML
	void toggleContMove(ActionEvent actionEvent) {
		contMove = ((CheckBox) actionEvent.getSource()).isSelected();
		if (contMove) {
			setTickDelay(500);
		} else {
			setTickDelay(0);
		}
		ensureKeyboardFocus();
	}

	@FXML
	private void handleTick() {
		step(playerDx, playerDy);
	}

	void newFood() {
		int x = playerX, y = playerY;
		while (x == playerX && y == playerY) {
			x = randomInt(0, imageGrid.getColumnCount() - 1);
			y = randomInt(0, imageGrid.getRowCount() - 1);
		}
		int value = randomInt(1, 9);
		setCell(x, y, value);
	}

	void updateStatus() {
		long millis = System.currentTimeMillis() - startTime;
		double pointsPrMillis = (points * 1000 / millis);
		statusLabel.setText(((double) Math.round(pointsPrMillis * 1000)) / 1000 + " points pr. second (" + points + " points in " + millis / 1000 + " seconds)");
	}

	protected void step(int dx, int dy) {
		int nextX = playerX + dx, nextY = playerY + dy;
		if (isValidXY(nextX, nextY)) {
			int cell = getCell(nextX, nextY);
			movePlayerTo(nextX, nextY);
			if (cell > 0) {
				points += cell;
				newFood();
			}
			updateStatus();
		}
	}
	
	@Override
	protected boolean keyPressed(int dx, int dy) {
		playerDx = dx;
		playerDy = dy;
		if (! contMove) {
			step(dx, dy);
		}
		return true;
	}

	//
	
	public static void main(String[] args) {
		launch(Eat.class);
	}
}
