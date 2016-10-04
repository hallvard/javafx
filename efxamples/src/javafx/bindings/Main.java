package javafx.bindings;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Slider celsius = new Slider(-15, 30, 10);
		System.out.println(celsius.minProperty().get() + " < " + celsius.valueProperty().get() + " < " + celsius.maxProperty().get());

		Slider fahrenheit = new Slider(c2f(-15), c2f(30), c2f(10));
		fahrenheit.valueProperty().bind(
//				new CelciusFahrenheitBinding(celsius.valueProperty())
				Bindings.multiply(1.8, celsius.valueProperty()).add(32)
				);
		System.out.println(fahrenheit.minProperty().get() + " < " + fahrenheit.valueProperty().get() + " < " + fahrenheit.maxProperty().get());

		HBox root = new HBox(10, celsius, fahrenheit);
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Converter");
		primaryStage.show();
	}

	private double c2f(double c) {
		double f = CelciusFahrenheitBinding.c2f(c);
		System.out.println(c + " -> " + f);
		return Math.round(f);
	}

	public static void main(String[] args){
		launch(args);
	}
}