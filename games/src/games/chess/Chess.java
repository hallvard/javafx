package games.chess;

import games.imagegrid.ImageGridGame;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Chess extends ImageGridGame<String> {

	String pawn = "Pawn";
	String[] pieces = {pawn, "kNight", "Bishop", "Rook", "Queen", "King"};
	char[] whitePieces = {'\u2659', '\u2658', '\u2657', '\u2656', '\u2655', '\u2654'};
	char[] blackPieces = {'\u265F', '\u265E', '\u265D', '\u265C', '\u265B', '\u265A'};

	String[] initialRow = {"Rook", "kNight", "Bishop", "Queen", "King", "Bishop", "kNight", "Rook"};

	char findPieceChar(String piece) {
		for (int i = 0; i < piece.length(); i++) {
			char c = piece.charAt(i);
			if (Character.isUpperCase(c)) {
				return c;
			}
		}
		return 0;
	}

	char findPieceChar(String piece, char[] pieceChars) {
		for (int i = 0; i < pieces.length; i++) {
			if (pieces[i] == piece) {
				return pieceChars[i];
			}
		}
		return 0;
	}
	
	@FXML
	protected void initialize() {
		super.initialize();
		imageGrid.setCellColors(2, "white", "grey", "grey", "white");
		Font pieceFont = Font.font("Arial", 36);
		for (int i = 0; i < pieces.length; i++) {
			String piece = pieces[i];
			imageGrid.setImage(getPlayerPiece(true, piece), createTextImage(findPieceChar(piece, whitePieces), pieceFont, Color.BLACK, Color.WHITE));
			imageGrid.setImage(getPlayerPiece(false, piece), createTextImage(findPieceChar(piece, blackPieces), pieceFont, Color.BLACK, Color.BLACK));
		}
		newAction();
	}

	Boolean player = true;
	
	@FXML
	void newAction() {
		fillGrid(null);
		for (int i = 0; i < initialRow.length; i++) {
			setCell(i, 0, getPlayerPiece(false, initialRow[i]));
			setCell(i, imageGrid.getRowCount() - 1, getPlayerPiece(true, initialRow[i]));
		}
		for (int i = 0; i < imageGrid.getColumnCount(); i++) {
			setCell(i, 1, getPlayerPiece(false, pawn));
			setCell(i, imageGrid.getRowCount() - 2, getPlayerPiece(true, pawn));
		}
		player = true;
	}

	String getPlayerName(boolean player) {
		return player ? "White" : "Black";
	}
	char getPlayerChar(boolean player) {
		return getPlayerName(player).charAt(0);
	}
	String getPlayerPiece(boolean player, String piece) {
		return String.valueOf(getPlayerChar(player)) + findPieceChar(piece);
	}
	Boolean getPiecePlayer(String piece) {
		char playerChar = piece.charAt(0);
		if (getPlayerChar(true) == playerChar) {
			return true;
		} else if (getPlayerChar(false) == playerChar) {
			return false;
		}
		return null;
	}

	private String sourcePiece;
	private int sourceX, sourceY;
	
	@Override
	protected Boolean mouseMoved(int x, int y) {
		String piece = getCell(x, y);
		boolean accept = piece != null && getPiecePlayer(piece) == player;
		setCursorIf(accept, Cursor.HAND, Cursor.DEFAULT);
		return null;
	}

	@Override
	protected Boolean mousePressed(int x, int y) {
		sourceX = x;
		sourceY = y;
		sourcePiece = getCell(x, y);
		boolean accept = sourcePiece != null && getPiecePlayer(sourcePiece) == player;
		setCursorIf(accept, Cursor.CLOSED_HAND, Cursor.DEFAULT);
		return accept;
	}
	
	@Override
	protected boolean mouseDragged(int x, int y) {
		boolean acceptDrop = acceptDrop(sourcePiece, sourceX, sourceY, getCell(x, y), x, y);
		setCursorIf(acceptDrop, Cursor.CLOSED_HAND, Cursor.WAIT);
		return acceptDrop;
	}

	@Override
	protected boolean mouseReleased(int x, int y) {
		setCursor(Cursor.DEFAULT);
		if (acceptDrop(sourcePiece, sourceX, sourceY, getCell(x, y), x, y)) {
			String cell = getCell(sourceX, sourceY);
			setCell(sourceX, sourceY, null);
			setCell(x, y, cell);
			player = ! player;
			return true;
		}
		return false;
	}

	//

	boolean acceptDrop(String source, int sourceX, int sourceY, String target, int targetX, int targetY) {
		// can only take opponent's pieces
		if (target != null && getPiecePlayer(target) == getPiecePlayer(source)) {
			return false;
		}
		int dx = targetX - sourceX, dy = targetY - sourceY;
		boolean player = getPiecePlayer(source);
		char pieceChar = source.charAt(1);
		if (pieceChar != 'N') {
			int ddx = (int) Math.signum(dx), ddy = (int) Math.signum(dy);
			int x = sourceX + ddx, y = sourceY + ddy;
			// check each cell to (but not including) the target
			while (x != targetX || y != targetY) {
				if (getCell(x, y) != null) {
					return false;
				}
				x += ddx;
				y += ddy;
			}
		}
		switch (pieceChar) {
		case 'P':
			if (target == null) {
				return (dx == 0) && Math.signum(dy) == (player ? -1 : 1) && (Math.abs(dy) == 1 || (Math.abs(dy) == 2 && sourceY == (player ? 6 : 1)));
			} else {
				return Math.abs(dx) == 1 && Math.abs(dy) == 1 && Math.signum(dy) == (player ? -1 : 1);
			}
		case 'N': return Math.abs(dx) * Math.abs(dy) == 2;
		case 'B': return Math.abs(dx) == Math.abs(dy);
		case 'R': return dx == 0 || dy == 0;
		case 'Q': return Math.abs(dx) == Math.abs(dy) || dx == 0 || dy == 0;
		case 'K': return Math.abs(dx) <= 1 && Math.abs(dy) <= 1;
		}
		return false;
	}
	
	public static void main(String[] args) {
		launch(Chess.class);
	}
}
