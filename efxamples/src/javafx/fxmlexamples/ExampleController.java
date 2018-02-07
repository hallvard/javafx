package javafx.fxmlexamples;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class ExampleController {

	@FXML
	TextField textInput;

	@FXML
	CheckBox autoUpcase;

	@FXML
	public void initialize() {
		textInput.setText("små bokstaver!");		
	}
	
	@FXML
	public void handleTextChange(Property<String> prop, String oldValue, String newValue) {
		if (autoUpcase.isSelected()) {
			textInput.setText(newValue.toUpperCase());
		}
	}

	@FXML
	public void handleUpcaseAction() {
		String s = textInput.getText();
		textInput.setText(s.toUpperCase());
	}
}
