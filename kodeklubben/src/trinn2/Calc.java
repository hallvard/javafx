package trinn2;

import java.io.IOException;
import java.util.function.BinaryOperator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Calc extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        Parent root = (Parent) fxmlLoader.load(this.getClass().getResourceAsStream("Calc.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private Double operand1 = null;
    private Double operand2 = null;
    private String operandString = null;
    private String operator = null;

    @FXML
    private TextField valueField;

    @FXML
    private void append(ActionEvent actionEvent) {
    	String label = ((Button) actionEvent.getSource()).getText();
    	if (operandString == null) {
    		operandString = "";
    	}
    	operandString += label;
    	update();
    }
    
    private void update() {
    	String text = operandString;
    	if (text == null) {
    		Double operand = operand2;
    		if (operand == null) {
    			operand = operand1;
    		}
			text = String.valueOf(operand);
    	}
    	valueField.setText(text);
    }

    private void pushOperand() {
    	Double operand = operand1;
    	if (operandString != null) {
    		try {
				operand = Double.valueOf(operandString);
			} catch (NumberFormatException e) {
			}
    	}
    	operandString = null;
    	if (operand1 == null) {
    		operand1 = operand;
    	} else {
    		operand2 = operand;
    	}
    }
    
    @FXML
    private void op(ActionEvent actionEvent) {
    	String label = ((Button) actionEvent.getSource()).getText();
    	pushOperand();
    	operator = label;
    	update();
    }

    private void compute(BinaryOperator<Double> op) {
    	pushOperand();
    	if (operand1 != null && operand2 != null) {
    		operand1 = op.apply(operand1, operand2);
    	}
    	operand2 = null;
    	update();
    }
    
    @FXML
    private void compute() {
    	switch (operator) {
    	case "+": compute((n1, n2) -> n1 + n2); break;
    	case "-": compute((n1, n2) -> n1 - n2); break;
    	case "*": compute((n1, n2) -> n1 * n2); break;
    	case "/": compute((n1, n2) -> n1 / n2); break;
    	}
    }

    public static void main(String[] args) {
        launch(args);
    }
}