package games.tictactoe.tictactoe1;

import games.imagegrid.ImageGridGame;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TicTacToeFX extends ImageGridGame<Boolean> {

	@FXML
	private ITicTacToe ticTacToe;

	@FXML
	private Text messageText;
	
	@FXML
	protected void initialize() {
		super.initialize();
		Font pieceFont = Font.font("Arial", 36);
		imageGrid.setImage(true, createTextImage('x', pieceFont, Color.BLACK, Color.BLACK));
		imageGrid.setImage(false, createTextImage('o', pieceFont, Color.BLACK, Color.BLACK));
		ticTacToe = new TicTacToe();
	}
	
	@FXML
	private void startGame() {
		imageGrid.setCellColors(1, "white");
		ticTacToe.init(null);
		fillGrid(null);
		updateStatus();
		ensureKeyboardFocus();
	}

	@Override
	protected boolean mouseClicked(int x, int y) {
		if (ticTacToe.getWinner() == null && ticTacToe.countCells(null) > 0) {
			ticTacToe.placePiece(x, y);
			setCell(x, y, ticTacToe.getCellValue(x, y));
			updateStatus();
		}
		return true;
	}

	private void updateStatus() {
		Boolean winner = ticTacToe.getWinner();
		if (winner != null) {
			messageText.setText((winner ? 'x' : 'o') + " wins!");
		} else {
			messageText.setText("x:" + ticTacToe.countCells(true) + " - " + "o:" + ticTacToe.countCells(false));
		}
	}

	public static void main(String[] args) {
		launch(TicTacToeFX.class);
	}
}
