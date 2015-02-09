package javafx.bindings;

import java.util.function.Function;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class StringFunctionBinding<T> extends StringBinding {

	private Function<T, String> function;
	private ObservableValue<? extends T> observableValue;
	
	public StringFunctionBinding(Function<T, String> function, ObservableValue<? extends T> observableValue) {
		this.function = function;
		setObservableValue(observableValue);
	}

	public void setObservableValue(ObservableValue<? extends T> observableValue) {
		if (this.observableValue != null) {
			super.unbind(this.observableValue);
		}
		this.observableValue = observableValue;
		invalidate();
		if (this.observableValue != null) {
			super.bind(this.observableValue);
		}
	}

	@Override
	protected String computeValue() {
		return (observableValue != null ? function.apply(observableValue.getValue()) : null);
	}
	
	public static void main(String[] args) {
		IntegerProperty hour = new SimpleIntegerProperty(8);
		IntegerProperty min = new SimpleIntegerProperty(15);
		StringProperty stringProperty = new SimpleStringProperty();
		StringFunctionBinding<Number> binding = new StringFunctionBinding<Number>(
			(n) -> String.format("%02d", n),
			hour
		);
		stringProperty.bind(binding);
		System.out.println(stringProperty.getValue());
		hour.set(9);
		System.out.println(stringProperty.getValue());
		binding.setObservableValue(min);
		System.out.println(stringProperty.getValue());
	}
}
