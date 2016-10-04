package fxmlbox2d.css;

import org.jbox2d.dynamics.FixtureDef;

import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;

public class FixtureDefBean<S extends Styleable> extends Box2dBean<S> {

	private final StyleableProperty<Number> density;
	private final StyleableProperty<Number> friction;
	private final StyleableProperty<Number> restitution;
	private final StyleableProperty<Boolean> sensor;

	public FixtureDefBean(S owner, StyleablePropertyFactory<S> spf) {
		super(owner, spf);
		this.density = createStyleableNumberProperty("density", s -> densityProperty(), 1.0);
		this.friction = createStyleableNumberProperty("friction", s -> frictionProperty(), 0.0);
		this.restitution = createStyleableNumberProperty("restitution", s -> restitutionProperty(), 0.0);
		this.sensor = createStyleableBooleanProperty("sensor", s -> sensorProperty(), false);
	}

	public FixtureDef createFixtureDef() {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.setDensity(this.getDensity());
		fixtureDef.setFriction(this.getFriction());
		fixtureDef.setRestitution(this.getRestitution());
		fixtureDef.setSensor(this.isSensor());
		return fixtureDef;
	}

	public StyleableProperty<Number> densityProperty() {
		return density;
	}
	public final Float getDensity() {
		return densityProperty().getValue().floatValue();
	}
	public final void setDensity(Float density) {
		densityProperty().setValue(density);
	}
	
	public StyleableProperty<Number> frictionProperty() {
		return friction;
	}
	public final Float getFriction() {
		return frictionProperty().getValue().floatValue();
	}
	public final void setFriction(Float friction) {
		frictionProperty().setValue(friction);
	}
	
	public StyleableProperty<Number> restitutionProperty() {
		return restitution;
	}
	public final Float getRestitution() {
		return restitutionProperty().getValue().floatValue();
	}
	public final void setRestitution(Float restitution) {
		restitutionProperty().setValue(restitution);
	}

	public StyleableProperty<Boolean> sensorProperty() {
		return sensor;
	}
	public final boolean isSensor() {
		return sensorProperty().getValue();
	}
	public final void setSensor(boolean sensor) {
		sensorProperty().setValue(sensor);
	}
}
