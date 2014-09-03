package javafx.subcontrollerexample;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {
	
	private SimpleStringProperty nameProperty = new SimpleStringProperty();
	private SimpleStringProperty emailProperty = new SimpleStringProperty();
	
	public String getName() {
		return nameProperty.getValue();
	}
	public void setName(String name) {
		nameProperty.setValue(name);
	}
	public ReadOnlyProperty<String> nameProperty() {
		return nameProperty;
	}

	public String getEmail() {
		return emailProperty.getValue();
	}
	public void setEmail(String email) {
		emailProperty.setValue(email);
	}
	public ReadOnlyProperty<String> emailProperty() {
		return emailProperty;
	}
}
