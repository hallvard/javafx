package games.cards;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Transform;

public abstract class CardsView extends Group {

	public void addCard(CardView card) {
		getChildren().add(card);
		updateCards();
	}

	public CardView addCard(int suitNum, int cardNum) {
		CardView card = new CardView(suitNum, cardNum);
		addCard(card);
		return card;
	}

	public CardView addCard() {
		CardView card = new CardView();
		addCard(card);
		return card;
	}

	public int getCardCount() {
		int count = 0;
		for (Node node : getChildrenUnmodifiable()) {
			if (node instanceof CardView) {
				count++;
			}
		}
		return count;
	}

	public abstract Transform getCardTransform(CardView card, int num);
	
	protected void updateCards() {
		int num = 0;
		for (Node node : getChildrenUnmodifiable()) {
			if (node instanceof CardView) {
				CardView card = (CardView) node;
				Transform transform = getCardTransform(card, num);
				ObservableList<Transform> transforms = node.getTransforms();
				transforms.clear();
				transforms.add(transform);
				num++;
			}
		}
	}
}
