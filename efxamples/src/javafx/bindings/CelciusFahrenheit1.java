package javafx.bindings;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CelciusFahrenheit1 extends Application {

	public void start(Stage stage) {
		Slider celsiusSlider = new Slider(-15, 30, 10);
		celsiusSlider.setShowTickMarks(true);
		celsiusSlider.setShowTickLabels(true);
		Slider fahrenheitSlider = new Slider(c2f(-15), c2f(30), c2f(10));
		fahrenheitSlider.setShowTickMarks(true);
		fahrenheitSlider.setShowTickLabels(true);
		
		fahrenheitSlider.valueProperty().bind(
				new CelciusFahrenheitBinding(celsiusSlider.valueProperty())
//				Bindings.multiply(1.8, celsiusSlider.valueProperty()).add(32)
		);
//		celsiusSlider.valueProperty().bind(
//				Bindings.subtract(fahrenheitSlider.valueProperty(), 32).divide(1.8)
//				);
		HBox root = new HBox(10, new Label("Celcius: "), celsiusSlider, new Label("Fahrenheit: "), fahrenheitSlider);
		stage.setScene(new Scene(root));
		stage.setTitle("Temperature Converter");
		stage.show();
	}
	
	private double c2f(double celcius) {
		return celcius * 1.8 + 32;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}