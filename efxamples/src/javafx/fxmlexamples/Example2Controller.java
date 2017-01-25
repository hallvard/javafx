package javafx.fxmlexamples;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Example2Controller {

	@FXML
	private TextField textField;

	@FXML
	public void handleUpcaseAction() {
		textField.setText(textField.getText().toUpperCase());
	}
}
