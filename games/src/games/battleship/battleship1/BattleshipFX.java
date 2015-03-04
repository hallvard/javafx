package games.battleship.battleship1;

import games.imagegrid.ImageGridGame;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class BattleshipFX extends ImageGridGame<String> {

	@FXML private TextField player1LevelTextField;
	@FXML private TextField player2LevelTextField;

	private TextField[] playerLevelTextFields;
	
	@FXML
	private Text messageText;

	@FXML private IBattleship battleship1;
	@FXML private IBattleship battleship2;
	
	private IBattleship[] battleships;

	@FXML
	protected void initialize() {
		super.initialize();
		playerLevelTextFields = new TextField[]{player1LevelTextField, player2LevelTextField};
		battleships = new IBattleship[]{battleship1, battleship2};
	}

	private int player = 0, winner = -1; // 0 or 1, index into arrays
	private String[] responses = {", you're up!", ", it's time to show who's boss.", ", fire at will!", ", don't just sit there.", ", you know what to do."};

	private String randomResponse() {
		return responses[(int) (Math.random() * responses.length)];
	}

	@FXML
	private void startGame() {
		fillGrid(null);
		int size = 0;
		for (int player = 0; player < battleships.length; player++) {
			String levelText = playerLevelTextFields[player].getText();
			battleships[player].init(levelText);
			if (size == 0) {
				size = battleships[player].getSize();
			} else if (battleships[player].getSize() != size) {
				throw new IllegalArgumentException("The level sizes are not the same!");
			}
		}
		imageGrid.setDimensions(size, size);
		updateCells();
		updateStatus("Game started. It is Player " + player + "'s turn.");
	}

	private void updateStatus(String status) {
		messageText.setText(status);
	}

	private void updateCell(int x, int y) {
		setCell(x, y, String.valueOf(battleships[player].getCellCharacter(x, y)));
	}

	private void updateCells() {
		foreachCell((x, y) -> updateCell(x, y));
	}

	
	@Override
	protected boolean mouseClicked(int x, int y) {
		if (winner < 0) {
			IBattleship battleship = battleships[player];
			boolean hit = battleship.fire(x, y);
			updateCell(x, y);
			int notHitShipCount = battleship.countShips(false);
			if (notHitShipCount == 0) {
				winner = player;
				updateStatus("GAME OVER! Player " + (winner + 1) + " won.");
			} else {
				String status = "Player " + (player + 1) + (hit ? " hit!" : "missed!");
				player = (player + 1) % 2;
				runDelayed(2000, () -> {
					updateCells();
					updateStatus(status + " Player " + (player + 1) + randomResponse());
				});
			}
		}
		return true;
	}

	public static void main(String[] args) {
		launch(BattleshipFX.class);
	}
}