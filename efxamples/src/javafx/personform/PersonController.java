package javafx.personform;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PersonController {

	private Person model;
	
	@FXML TextField nameField;
	@FXML Label nameFieldRule;
	@FXML TextField emailField;
	
	public void initialize() {
//		model = new Person();
		model = new Person("Hallvard Trætteberg", "hal@idi.ntnu.no");
		updateNameView();
		emailField.setText(model.getEmail());
	}

	private void updateNameView() {
		nameField.setText(model.getName());
	}
	
	@FXML void nameFieldChange(ObservableValue<? extends String> property, String oldValue, String newValue) {
		validateNameView(newValue);
	}

	private void validateNameView(String newValue) {
		validate(newValue, "[\\p{L}\\.\\-\\s)]*", nameField, nameFieldRule);
	}
	@FXML void nameFieldFocusChange(ObservableValue<? extends Boolean> property, Boolean oldValue, Boolean newValue) {
		if (! newValue) {
			try {
				updateNameModel();
			} catch (Exception e) {
				
			}
			updateNameView();
		}
	}

	private void updateNameModel() {
		model.setName(nameField.getText());
	}

	@FXML void emailFieldChange(ObservableValue<? extends String> property, String oldValue, String newValue) {
		validate(newValue, "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", emailField, null);
	}

	void validate(String value, String regex, TextField textField, Label ruleField) {
		boolean isValid = value.matches(regex);
		String color = isValid ? "white" : "red";
		textField.setStyle("-fx-background-color: " + color);
		if (ruleField != null) {
			ruleField.setVisible(! isValid);
		}
	}
}
