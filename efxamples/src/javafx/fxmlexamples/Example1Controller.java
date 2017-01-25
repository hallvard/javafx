package javafx.fxmlexamples;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class Example1Controller {
	
	@FXML
	TextField textOutput;
	@FXML
	Button clickMeButton;
	
	@FXML
	void initialize() {
//		clickMeButton.setOnAction((event) -> { handleClickMeAction();});
	}
	
	@FXML
	void handleTextChange(StringProperty prop, String oldValue, String newValue) {
		textOutput.setText(newValue.toUpperCase());
	}

	@FXML
	void checkBoxSelected(BooleanProperty prop, Boolean oldValue, Boolean newValue) {
		System.out.println("Changed from " + oldValue + " to " + newValue);
	}

	@FXML
	void handleClickMeAction(ActionEvent event) {
		System.out.println(event);
		textOutput.setText(textOutput.getText().toLowerCase());
		System.out.println("Hei");
	}
}
