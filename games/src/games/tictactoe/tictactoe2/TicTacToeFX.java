package games.tictactoe.tictactoe2;

import games.IUpdateable;
import games.PersistableController;
import games.UndoableController;
import games.imagegrid.ImageGridGame;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TicTacToeFX extends ImageGridGame<Boolean> implements IUpdateable {

	@FXML
	private ITicTacToe ticTacToe;
	
	@FXML
	private UndoableController undoableController;

	@FXML
	private PersistableController persistableController;

	@FXML
	private Text messageText;
	
	@FXML
	protected void initialize() {
		super.initialize();
		Font pieceFont = Font.font("Arial", 36);
		imageGrid.setImage(true, createTextImage('x', pieceFont, Color.BLACK, Color.BLACK));
		imageGrid.setImage(false, createTextImage('o', pieceFont, Color.BLACK, Color.BLACK));
		ticTacToe = new TicTacToe();
		
		undoableController.setUndoRedo(ticTacToe);
		undoableController.setUpdate(this);
		persistableController.setStateStore(ticTacToe);
		persistableController.setUpdate(this);
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
			updateCell(x, y);
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

	@Override
	public void updateState(boolean fullUpdate) {
		updateCells(0, 0, ticTacToe.getWidth(), ticTacToe.getHeight());
		updateStatus();
	}

	@Override
	public void updateState(String text) {
		messageText.setText(text);
	}
	
	private void updateCell(int x, int y) {
		setCell(x, y, ticTacToe.getCellValue(x, y));
	}
	
	private void updateCells(int x1, int y1, int x2, int y2) {
		for(int x = x1; x < x2; ++x) {
			for(int y = y1; y < y2; ++y) {
				updateCell(x, y);
			}
		}
	}
	
	public static void main(String[] args) {
		launch(TicTacToeFX.class);
	}
}
