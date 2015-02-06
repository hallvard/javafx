package games.cards;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.transform.Transform;

public class CardFanView extends CardsView {

	private double cardRotate0, cardRotate;
	
	public CardFanView(double rotate) {
		this.cardRotate = rotate;
	}

	public CardFanView() {
		this(0.0);
	}

	private DoubleProperty cardRotateProperty;

	public DoubleProperty cardRotateProperty() {
		if (cardRotateProperty == null) {
			cardRotateProperty = new SimpleDoubleProperty(cardRotate);
		}
		return cardRotateProperty;
	}
	
	public double getCardRotate() {
		return (cardRotateProperty != null ? cardRotateProperty.get() : cardRotate);
	}
	
	public void setCardRotate(double rotate) {
		if (cardRotateProperty != null) {
			cardRotateProperty.set(rotate);
		} else {
			this.cardRotate = rotate;
		}
		updateCards();
	}

	private double fanArcOffset = 20;

	private DoubleProperty fanArcOffsetProperty;

	public DoubleProperty fanArcOffsetProperty() {
		if (fanArcOffsetProperty == null) {
			fanArcOffsetProperty = new SimpleDoubleProperty(fanArcOffset);
		}
		return fanArcOffsetProperty;
	}
	
	public double getFanArcOffset() {
		return (fanArcOffsetProperty != null ? fanArcOffsetProperty.get() : fanArcOffset);
	}

	public void setFanArcOffset(double fanArcOffset) {
		if (fanArcOffsetProperty != null) {
			fanArcOffsetProperty.set(fanArcOffset);
		} else {
			this.fanArcOffset = fanArcOffset;
		}
		updateCards();
	}

	@Override
	protected void updateCards() {
		int cardCount = getCardCount();
		cardRotate0 = -(this.cardRotate * cardCount / 2 + fanArcOffset);
		super.updateCards();
	}

	private double fanArcFactor = 1.08;
	
	private DoubleProperty fanArcFactorProperty;

	public DoubleProperty fanArcFactorProperty() {
		if (fanArcFactorProperty == null) {
			fanArcFactorProperty = new SimpleDoubleProperty(fanArcFactor);
		}
		return fanArcOffsetProperty;
	}
	
	public double getFanArcFactor() {
		return (fanArcFactorProperty != null ? fanArcFactorProperty.get() : fanArcFactor);
	}
	
	public void setFanArcFactor(double fanArcFactor) {
		if (fanArcFactorProperty != null) {
			fanArcFactorProperty.set(fanArcFactor);
		} else {
			this.fanArcFactor = fanArcFactor;
		}
		updateCards();
	}
	
	@Override
	public Transform getCardTransform(CardView card, int num) {
		return Transform.rotate(cardRotate0 + cardRotate * num, 0, card.getBoundsInLocal().getHeight() * fanArcFactor);
	}
}
