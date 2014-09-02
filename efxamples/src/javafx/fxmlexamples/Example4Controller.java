package javafx.fxmlexamples;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Example4Controller {

	@FXML
	private TextField textField;

	@FXML
	public void handleUpcaseAction(ActionEvent event) {
		textField.setText(textField.getText().toUpperCase());
	}
}
