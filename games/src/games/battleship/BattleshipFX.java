package games.battleship;

import games.imagegrid.ImageGridGame;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class BattleshipFX extends ImageGridGame<String> {

	@FXML
	private TextField player1LevelTextField;
	
	@FXML
	private TextField player2LevelTextField;

	@FXML
	private Text messageText;

	@FXML
	protected void initialize() {
		super.initialize();
	}

	@FXML
	private IBattleship battleship;

	private IBattleship[] battleships = new IBattleship[2];
	private int player = 1; // 1 or 2
	private int winner = -1;
	private int selectedX, selectedY;
	private String[] responses = {", you're up!", ", it's time to show who's boss.", ", fire at will!", ", don't just sit there.", ", you know what to do."};

	private String randomResponse() {
		return responses[(int) (Math.random() * responses.length)];
	}
	
	@FXML
	private void startGame() {
		
		fillGrid(null);
		String p1Level = player1LevelTextField.getText();
		String p2Level = player2LevelTextField.getText();
		
		if (p1Level.length() != p2Level.length())
			throw new IllegalArgumentException("The two levels are not of the same dimensions!");
		
		battleship.init(p1Level);
		battleships[0] = battleship;
		battleships[1] = new Battleship();
		battleships[1].init(p2Level);

		imageGrid.setRowCount(battleship.getDimension());
		imageGrid.setColumnCount(battleship.getDimension());
		for (int y = 0; y < battleship.getDimension(); y++) {
			for (int x = 0; x < battleship.getDimension(); x++) {
				setCell(x, y, battleship.getCellString(x, y));
			}
		}
		updateStatus("Game started. It is Player " + player + "'s turn.");
	}

	private void updateStatus(String status) {
		messageText.setText(status);
	}

	@Override
	protected boolean mouseClicked(int x, int y) {
		selectedX = x;
		selectedY = y;
		update();
		return true;
	}

	private void update() {

		boolean hit = battleship.fire(selectedX, selectedY);
		setCell(selectedX, selectedY, battleship.getCellString(selectedX, selectedY));

		if (battleship.isGameOver() && winner == -1) winner = player;

		int nextPlayer = (player == 1) ? 2 : 1;
		
		if (winner != -1) updateStatus("GAME OVER! Player " + winner + " won.");
		else {
			String status =  (hit) ? "Player " + player + " hit! " : "Player " + player + " missed! ";
			updateStatus(status + "Player " + nextPlayer + randomResponse());
		}

		player = nextPlayer;
		battleship = (battleship == battleships[0]) ? battleships[1] : battleships[0];
		foreachCell((x, y) -> setCell(x, y, battleship.getCellString(x, y)));
	}

	public static void main(String[] args) {
		launch(BattleshipFX.class);
	}
}