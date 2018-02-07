package javafx.masterdetail;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.ListCell;

public class PersonCell extends ListCell<Person> {
	
	private ChangeListener<String> nameChangeListener = 
			(property, oldValue, newValue) -> { setText(computeTextValue(getItem())); };
	private ChangeListener<String> emailChangeListener = 
			(property, oldValue, newValue) -> { setText(computeTextValue(getItem())); };
	
	public PersonCell() {
		System.out.println("Created");
	}
			
	@Override
	protected void updateItem(Person item, boolean empty) {
		System.out.println("Updated to view " + item + " (" + empty + ")");
		Person oldItem = getItem();
		if (oldItem == item) {
			return;
		}
		if (oldItem != null) {
			oldItem.nameProperty().removeListener(nameChangeListener);
			oldItem.emailProperty().removeListener(emailChangeListener);
		}
		super.updateItem(item, empty);
		if (item != null) {
			item.nameProperty().addListener(nameChangeListener);
			item.emailProperty().addListener(emailChangeListener);
		}
		setText(item == null || empty ? "" : computeTextValue(item));
	}

	private String computeTextValue(Person item) {
		String nameText = (item != null && item.getName() != null ? item.getName() : "<no name>");
		String emailText = (item != null && item.getEmail() != null ? item.getEmail() : "<no email>");
		return nameText + " -> " + emailText;
	}
}
