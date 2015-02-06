package games.borderline;

import games.imagegrid.ImageGridGame;
import javafx.fxml.FXML;
import javafx.scene.control.Labeled;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Borderline extends ImageGridGame<Boolean> {

	@FXML
	Labeled statusLabel;

	int playerX, playerY;
	int playerDx = 0, playerDy = 0;
	int speed = 10;
	
	@FXML
	protected void initialize() {
		super.initialize();
		for (int num = 0; num < 10; num++) {
			imageGrid.setCellColors(1, "white");
			imageGrid.setImage(true, createTextImage('☺', Font.font("Arial", 64), Color.WHITE, Color.BLACK));
			imageGrid.setImage(false, createTextImage('☹', Font.font("Arial", 64), Color.WHITE, Color.BLACK));
		}
		fillGrid(null);
	}

	@FXML
	void restartAction() {
		fillGrid(null);
		playerDx = 0;
		playerDy = 0;
		int corner = randomInt(0, 3);
		playerX = (corner % 2 == 0 ? 0 : 2);
		playerY = (corner >= 2 ? 0 : 2);
		boolean isDx = randomBoolean();
		if (isDx) {
			playerDx = (corner % 2 == 0 ? 1 : -1);
		} else {
			playerDy = (corner >= 2 ? 1 : -1);
		}
		movePlayerTo(playerX, playerY);
		speed = 10;
		setTickDelay(speed2Delay());
		ensureKeyboardFocus();
	}

	private int speed2Delay() {
		return 10000 / speed;
	}

	private void movePlayerTo(int x, int y) {
		setCell(playerX, playerY, null);
		playerX = x;
		playerY = y;
		setCell(playerX, playerY, true);
	}

	protected void step() {
	}

	@FXML
	private void handleTick() {
		int nextX = playerX + playerDx, nextY = playerY + playerDy;
		if (! isValidXY(nextX, nextY)) {
			setTickDelay(0);
			playerDx = playerDy = 0;
			setCell(playerX, playerY, false);
			statusLabel.setText("Final speed: " + speed);
		} else {
			movePlayerTo(nextX, nextY);
			updateStatus();
		}
		speed *= 1.1;
		setTickDelay(speed2Delay());
	}

	void updateStatus() {
		statusLabel.setText("Speed: " + speed);
	}

	@Override
	protected boolean keyPressed(int dx, int dy) {
		if (playerDx == 0 && playerDy == 0) {
			return false;
		}
		playerDx = dx;
		playerDy = dy;
		return true;
	}

	//
	
	public static void main(String[] args) {
		launch(Borderline.class);
	}
}
