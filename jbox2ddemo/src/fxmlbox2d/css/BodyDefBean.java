package fxmlbox2d.css;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;

public class BodyDefBean<S extends Styleable> extends Box2dBean<S> {

	private final StyleableProperty<BodyType> bodyType;
	private final StyleableProperty<Number> linearDamping;
	private final StyleableProperty<Number> angularDamping;
	private final StyleableProperty<Number> gravityScale;
	private final StyleableProperty<Boolean> allowSleep;
	private final StyleableProperty<Boolean> awake;
	private final StyleableProperty<Boolean> fixedRotation;
	private final StyleableProperty<Boolean> active;

	public BodyDefBean(S owner, StyleablePropertyFactory<S> spf) {
		super(owner, spf);
		this.bodyType = createStyleableEnumProperty("bodyType", s -> bodyTypeProperty(), BodyType.class, BodyType.STATIC);
		this.linearDamping = createStyleableNumberProperty("linearDamping", s -> linearDampingProperty(), 0.0);
		this.angularDamping = createStyleableNumberProperty("angularDamping", s -> angularDampingProperty(), 0.0);
		this.gravityScale = createStyleableNumberProperty("gravityScale", s -> gravityScaleProperty(), 1.0);
		this.allowSleep = createStyleableBooleanProperty("allowSleep", s -> allowSleepProperty(), true);
		this.awake = createStyleableBooleanProperty("awake", s -> awakeProperty(), true);
		this.fixedRotation = createStyleableBooleanProperty("fixedRotation", s -> fixedRotationProperty(), false);
		this.active = createStyleableBooleanProperty("active", s -> activeProperty(), true);
	}

	public BodyDef createBodyDef() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.setType(this.getBodyType());
		bodyDef.setLinearDamping(this.getLinearDamping());
		bodyDef.setAngularDamping(this.getAngularDamping());
		bodyDef.setGravityScale(this.getGravityScale());
		bodyDef.setAllowSleep(this.isAllowSleep());
		bodyDef.setAwake(this.isAwake());
		bodyDef.setFixedRotation(this.isFixedRotation());
		bodyDef.setActive(this.isActive());
		return bodyDef;
	}

	public StyleableProperty<BodyType> bodyTypeProperty() {
		return bodyType;
	}
	public final BodyType getBodyType() {
		return bodyTypeProperty().getValue();
	}
	public final void setBodyType(BodyType bodyType) {
		bodyTypeProperty().setValue(bodyType);
	}
	
	public StyleableProperty<Number> linearDampingProperty() {
		return linearDamping;
	}
	public final Float getLinearDamping() {
		return linearDampingProperty().getValue().floatValue();
	}
	public final void setLinearDamping(Float linearDamping) {
		linearDampingProperty().setValue(linearDamping);
	}

	public StyleableProperty<Number> angularDampingProperty() {
		return angularDamping;
	}
	public final Float getAngularDamping() {
		return angularDampingProperty().getValue().floatValue();
	}
	public final void setAngularDamping(Float angularDamping) {
		angularDampingProperty().setValue(angularDamping);
	}
	
	public StyleableProperty<Number> gravityScaleProperty() {
		return gravityScale;
	}
	public final Float getGravityScale() {
		return gravityScaleProperty().getValue().floatValue();
	}
	public final void setGravityScale(Float gravityScale) {
		gravityScaleProperty().setValue(gravityScale);
	}
	
	public StyleableProperty<Boolean> allowSleepProperty() {
		return allowSleep;
	}
	public final boolean isAllowSleep() {
		return allowSleepProperty().getValue();
	}
	public final void setAllowSleep(boolean allowSleep) {
		allowSleepProperty().setValue(allowSleep);
	}
	
	public StyleableProperty<Boolean> awakeProperty() {
		return awake;
	}
	public final boolean isAwake() {
		return awakeProperty().getValue();
	}
	public final void setAwake(boolean awake) {
		awakeProperty().setValue(awake);
	}
	
	public StyleableProperty<Boolean> fixedRotationProperty() {
		return fixedRotation;
	}
	public final boolean isFixedRotation() {
		return fixedRotationProperty().getValue();
	}
	public final void setFixedRotation(boolean fixedRotation) {
		fixedRotationProperty().setValue(fixedRotation);
	}
	
	public StyleableProperty<Boolean> activeProperty() {
		return active;
	}
	public final boolean isActive() {
		return activeProperty().getValue();
	}
	public final void setActive(boolean active) {
		activeProperty().setValue(active);
	}
}
