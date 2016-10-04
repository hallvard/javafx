package fxmlbox2d.css;

import java.util.function.Function;

import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;

public class Box2dBean<S extends Styleable> {

	protected final static String CSS_PREFIX = "-bx-";
	
	private final S owner;
	private final StyleablePropertyFactory<S> spf;
	
	protected Box2dBean(S owner, StyleablePropertyFactory<S> spf) {
		this.owner = owner;
		this.spf = spf;
	}
	
	protected StyleableProperty<Boolean> createStyleableBooleanProperty(String propertyName, Function<S, StyleableProperty<Boolean>> propFun, boolean initial) {
		return spf.createStyleableBooleanProperty(owner, propertyName, CSS_PREFIX + propertyName, propFun, initial);
	}
	
	protected StyleableProperty<Number> createStyleableNumberProperty(String propertyName, Function<S, StyleableProperty<Number>> propFun, Number initial) {
		return spf.createStyleableNumberProperty(owner, propertyName, CSS_PREFIX + propertyName, propFun, initial);
	}
	
	protected final <E extends Enum<E>> StyleableProperty<E> createStyleableEnumProperty(String propertyName, Function<S, StyleableProperty<E>> propFun, Class<E> enumClass, E initial) {
		return spf.createStyleableEnumProperty(owner, propertyName, CSS_PREFIX + propertyName, propFun, enumClass, initial);
	}
}
