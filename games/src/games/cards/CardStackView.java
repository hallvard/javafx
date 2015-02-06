package games.cards;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.transform.Transform;

public class CardStackView extends CardsView {

	private double cardTranslateX, cardTranslateY;
	
	private DoubleProperty cardTranslateXProperty, cardTranslateYProperty;
	
	public DoubleProperty cardTranslateXProperty() {
		if (cardTranslateXProperty == null) {
			cardTranslateXProperty = new SimpleDoubleProperty();
		}
		return cardTranslateXProperty;
	}
	public DoubleProperty cardTranslateYProperty() {
		if (cardTranslateYProperty == null) {
			cardTranslateYProperty = new SimpleDoubleProperty();
		}
		return cardTranslateYProperty;
	}
	
	public double getCardTranslateX() {
		return (cardTranslateXProperty != null ? cardTranslateXProperty.get() : cardTranslateX);
	}
	public double getCardTranslateY() {
		return (cardTranslateYProperty != null ? cardTranslateYProperty.get() : cardTranslateY);
	}
	
	public void setCardTranslateX(double cardTranslateX) {
		if (cardTranslateXProperty != null) {
			cardTranslateXProperty.set(cardTranslateX);
		} else {
			this.cardTranslateX = cardTranslateX;
		}
		updateCards();
	}
	public void setCardTranslateY(double cardTranslateY) {
		if (cardTranslateYProperty != null) {
			cardTranslateYProperty.set(cardTranslateY);
		} else {
			this.cardTranslateY = cardTranslateY;
		}
		updateCards();
	}
	
	public CardStackView(double cardTranslateX, double cardTranslateY) {
		this.cardTranslateX = cardTranslateX;
		this.cardTranslateY = cardTranslateY;
	}

	public CardStackView() {
		this(0, 0);
	}

	public void setCardTranslate(double cardTranslateX, double cardTranslateY) {
		setCardTranslateX(cardTranslateX);
		setCardTranslateY(cardTranslateY);
	}
	
	@Override
	public Transform getCardTransform(CardView card, int num) {
		return Transform.translate(cardTranslateX * num, cardTranslateY * num);
	}
}
