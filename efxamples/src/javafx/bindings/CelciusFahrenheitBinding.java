package javafx.bindings;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;

public class CelciusFahrenheitBinding extends DoubleBinding {

	private ObservableValue<Number> celsius;
	
	public CelciusFahrenheitBinding(ObservableValue<Number> celsius) {
		this.celsius = celsius;
		bind(celsius);
	}
	
	@Override
	protected double computeValue() {
		double c = celsius.getValue().doubleValue();
		double f = c2f(c);
		System.out.println(c + " -> " + f);
		return f;
	}

	static double c2f(double c) {
		return c * 1.8 + 32;
	}
}
