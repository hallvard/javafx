package javafx.fxmlexamples;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Example3Controller {

	@FXML
	private TextField textField;

	@FXML
	public void handleUpcaseAction2(StringProperty property, String oldValue, String newValue) {
		textField.setText(textField.getText().toUpperCase());
	}
	
	@FXML
	public void handleUpcaseAction() {
		textField.setText(textField.getText().toUpperCase());
	}
}
