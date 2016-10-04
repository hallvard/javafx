package javafx.bindings;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class StringFormatBinding extends StringBinding {

	private String formatString;
	private ObservableValue<?>[] formatObservables;
	private Object[] formatArguments;
	
	public StringFormatBinding(String format, ObservableValue<?> ...observableValues) {
		this.formatString = format;
		for (int i = 0; i < observableValues.length; i++) {
			super.bind(observableValues[i]);
		}
		this.formatObservables = observableValues;
		this.formatArguments = new Object[observableValues.length];
	}

	@Override
	protected void onInvalidating() {
		System.out.println("Invalidating");
	}
	
	@Override
	protected String computeValue() {
		for (int i = 0; i < formatObservables.length; i++) {
			formatArguments[i] = formatObservables[i].getValue();
		}
		return String.format(formatString, formatArguments);
	}

	public static void main(String[] args) {
		IntegerProperty hour = new SimpleIntegerProperty(16);
		IntegerProperty min = new SimpleIntegerProperty(30);
		StringProperty stringProperty = new SimpleStringProperty();
		stringProperty.bind(new StringFormatBinding("%02d:%02d", hour, min));
		System.out.println(stringProperty.getValue());
		hour.set(8);
		System.out.println(stringProperty.getValue());
	}
}
