package javafx.tables;

import java.time.LocalDate;

import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;

public class Person {
	
	private Property<String> nameProperty = new SimpleStringProperty();
	private Property<String> emailProperty = new SimpleStringProperty();
	private Property<LocalDate> birthdayProperty = new ObjectPropertyBase<LocalDate>(null) {
		@Override
		public Object getBean() {
			return this;
		}
		@Override
		public String getName() {
			return "birthday";
		}
	};
	
	public String getName() {
		return nameProperty.getValue();
	}
	public void setName(String name) {
		nameProperty.setValue(name);
	}
	public Property<String> nameProperty() {
		return nameProperty;
	}

	public String getEmail() {
		return emailProperty.getValue();
	}
	public void setEmail(String email) {
		emailProperty.setValue(email);
	}
	public Property<String> emailProperty() {
		return emailProperty;
	}
	
	public LocalDate getBirthday() {
		return birthdayProperty.getValue();
	}
	public void setBirthday(LocalDate birthday) {
		birthdayProperty.setValue(birthday);
	}
	public Property<LocalDate> birthdayProperty() {
		return birthdayProperty;
	}
}
