package games.breakout;

import games.simulation.MovingObject;
import games.simulation.SimulationGame;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class BreakOut extends SimulationGame {

	@FXML
	Pane pane;
	
	@FXML
	Node playerShape;

	@FXML
	ObservableList<Node> bricks;

	@FXML
	void initialize() {
		player = new MovingObject(playerShape);
		setTickDelay(100);
	}
	
	MovingObject player;

	@Override
	protected void tick() {
		super.tick();
		player.tick();
		checkCollisions();
		bounce();
	}

	private void checkCollisions() {
		Bounds playerBounds = playerShape.getBoundsInParent();
		for (Node brick : bricks) {
			if (brick.isVisible()) {
				Bounds brickBounds = brick.getBoundsInParent();
				if (playerBounds.intersects(brickBounds)) {
					brick.setVisible(false);
				}
			}
		}
	}

	private void bounce() {
		Bounds playerBounds = playerShape.getBoundsInParent();
		if (playerBounds.getMinX() < 0) {
			player.raccelerate(-2, 0);
		} else if (playerBounds.getMaxX() > pane.getWidth()) {
			player.raccelerate(-2, 0);
		}
		if (playerBounds.getMinY() < 0) {
			player.raccelerate(0, -2);
		} else if (playerBounds.getMaxY() > pane.getHeight()) {
			player.raccelerate(0, -2);
		}
	}

	//
	
	@Override
	protected boolean keyPressed(int dx, int dy) {
		player.accelerate(dx, dy);
		return true;
	}

	public static void main(String[] args) {
		launch(BreakOut.class);
	}
}
