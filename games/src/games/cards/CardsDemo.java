package games.cards;

import games.FxmlApp;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CardsDemo extends FxmlApp {

	@Override
	public void start(Stage primaryStage) throws Exception {
		super.start(primaryStage);
	}
	
	@FXML
	private CardStackView stack;

	@FXML
	private CardFanView fan;

	@FXML
	private TextField txField, tyField;
	
	@FXML
	private void setTranslate() {
		double dx = Double.valueOf(txField.getText());
		double dy = Double.valueOf(tyField.getText());
		stack.setCardTranslate(dx, dy);
	}

	@FXML
	private TextField rotateField;
	
	@FXML
	private void setRotate() {
		double rotate = Double.valueOf(rotateField.getText());
		fan.setCardRotate(rotate);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
