package javafx.subcontrollerexample;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.ListCell;

class PersonCell extends ListCell<Person> {
	
	private ChangeListener<String> nameChangeListener = (property, oldValue, newValue) -> {
		updateItemInternal(getItem(), false);
	};
	private ChangeListener<String> emailChangeListener = (property, oldValue, newValue) -> {
		updateItemInternal(getItem(), false);
	};
	
	protected void updateItemInternal(Person item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText("");
		} else {
			setText(item.getName() + " -> " + item.getEmail());
		}
	}

	@Override
	protected void updateItem(Person item, boolean empty) {
		Person oldItem = getItem();
		boolean modelChanged = item != oldItem;
		if (modelChanged && oldItem != null) {
			oldItem.nameProperty().removeListener(nameChangeListener);
			oldItem.emailProperty().removeListener(emailChangeListener);
		}
		updateItemInternal(item, empty);
		if (modelChanged && item != null) {
			item.nameProperty().addListener(nameChangeListener);
			item.emailProperty().addListener(emailChangeListener);
		}
	}
}
