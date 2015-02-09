package javafx.bindings;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;

public class CelciusFahrenheitBinding extends DoubleBinding {

	private ObservableValue<Double> fahrenheit;
	
	public CelciusFahrenheitBinding(ObservableValue<Double> fahrenheit) {
		this.fahrenheit = fahrenheit;
		bind(fahrenheit);
	}
	
	@Override
	protected double computeValue() {
		return fahrenheit.getValue() * 1.8 + 32;
	}
}
