package javafx.fxmlexamples;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Example5Controller {

	@FXML
	TextField textField;

	@FXML
	Button upcaseButton;
	
	@FXML
	public void handleUpcaseAction(ActionEvent event) {
		textField.setText(textField.getText().toUpperCase());
	}
}
