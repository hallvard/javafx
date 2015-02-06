package games.cards;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class CardView extends TiledImageView {

	static {
		TiledImage.getTiledImage(getCardsImageUrl(), 5, 13);
	}
	
	public static String getCardsImageUrl() {
		return CardView.class.getResource("./cards.png").toString();
	}
	
	public static int SPADES = 0, HEARTS = 1, DIAMONDS = 2, CLUBS = 3;

	public static int[] SUITS = {SPADES, HEARTS, DIAMONDS, CLUBS};

	public static int FIRST_CARD = 1, LAST_CARD = 13;

	private static int JOKER_ROW = 4;

	private static int getTileRow(int suitNum, int cardNum) {
		return (suitNum >= 0 ? 3 - suitNum : JOKER_ROW);
	}

	private static int getTileColumn(int suit, int cardNum) {
		return cardNum - FIRST_CARD;
	}

	private static int FACE_DOWN_ROW = 4, FACE_DOWN_COLUMN = 2;
	
	private boolean faceDown = false;
	
	private int suitNum = -1, cardNum = -1;

	/**
	 * Specific card
	 * @param suitNum
	 * @param cardNum
	 */
	public CardView(int suitNum, int cardNum) {
		super(getCardsImageUrl(), getTileRow(suitNum, cardNum), getTileColumn(suitNum, cardNum));
		this.suitNum = suitNum;
		this.cardNum = cardNum;
	}

	/**
	 * Specific joker
	 * @param cardNum
	 */
	public CardView(int cardNum) {
		super(getCardsImageUrl(), JOKER_ROW, getTileColumn(-1, cardNum));
		this.cardNum = cardNum;
	}

	/**
	 * Uninitialized card
	 */
	public CardView() {
		super(getCardsImageUrl(), FACE_DOWN_ROW, FACE_DOWN_COLUMN);
	}
	
	protected void setTiledImage() {
		if (isFaceDown() || getCardNum() < 0) {
			setTiledImage(getCardsImageUrl(), FACE_DOWN_ROW, FACE_DOWN_COLUMN);
		} else if (getSuitNum() < 0) {
			setTiledImage(getCardsImageUrl(), JOKER_ROW, getTileColumn(-1, getCardNum()));
		} else {
			setTiledImage(getCardsImageUrl(), getTileRow(getSuitNum(), getCardNum()), getTileColumn(getSuitNum(), getCardNum()));
		}
	}
	
	private IntegerProperty suitNumProperty = null, cardNumProperty = null;

	public IntegerProperty suitNumProperty() {
		if (suitNumProperty == null) {
			suitNumProperty = new SimpleIntegerProperty(suitNum);
		}
		return suitNumProperty;
	}

	public IntegerProperty cardNumProperty() {
		if (cardNumProperty == null) {
			cardNumProperty = new SimpleIntegerProperty(cardNum);
		}
		return suitNumProperty;
	}
	
	public int getSuitNum() {
		return (suitNumProperty != null ? suitNumProperty.get() : suitNum);
	}
	
	public void setSuitNum(int suitNum) {
		if (suitNumProperty != null) {
			suitNumProperty().set(suitNum);
		} else {
			this.suitNum = suitNum; 
		}
		setTiledImage();
	}
	
	public int getCardNum() {
		return (cardNumProperty != null ? cardNumProperty.get() : cardNum);
	}
	
	public void setCardNum(int cardNum) {
		if (cardNumProperty != null) {
			cardNumProperty().set(cardNum);
		} else {
			this.cardNum = cardNum; 
		}
		setTiledImage();
	}

	private BooleanProperty faceDownProperty;
	
	public boolean isFaceDown() {
		return (faceDownProperty != null ? faceDownProperty.get() : faceDown);
	}
	
	public void setFaceDown(boolean faceDown) {
		if (faceDownProperty != null) {
			faceDownProperty.set(faceDown);
		} else {
			this.faceDown = faceDown;
		}
	}
}
