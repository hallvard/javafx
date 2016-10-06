package fxmlbox2d.css;

import java.util.List;

import org.jbox2d.dynamics.BodyType;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.Group;
import javafx.scene.layout.Region;

public class Body extends Group implements BodyDefBeanOwner {

	private static final StyleablePropertyFactory<Body> SPF = new StyleablePropertyFactory<>(Region.getClassCssMetaData());

	public static  List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return SPF.getCssMetaData();
    }

	private BodyDefBean<Body> bodyDefBean;
	
	public Body() {
		getStyleClass().add("body");
		this.bodyDefBean = new BodyDefBean<Body>(this, SPF);
	}

	@Override
	public BodyDefBean<? extends Styleable> getBodyDefBean() {
		return bodyDefBean;
	}

	@Override
	public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
		return getClassCssMetaData();
	}

	public StyleableProperty<BodyType> bodyTypeProperty() {
		return bodyDefBean.bodyTypeProperty();
	}
	public final BodyType getBodyType() {
		return bodyDefBean.getBodyType();
	}
	public final void setBodyType(BodyType bodyType) {
		bodyDefBean.setBodyType(bodyType);
	}
	
	public StyleableProperty<Number> linearDampingProperty() {
		return bodyDefBean.linearDampingProperty();
	}
	public final Float getLinearDamping() {
		return bodyDefBean.getLinearDamping();
	}
	public final void setLinearDamping(Float linearDamping) {
		bodyDefBean.setLinearDamping(linearDamping);
	}

	public StyleableProperty<Number> angularDampingProperty() {
		return bodyDefBean.angularDampingProperty();
	}
	public final Float getAngularDamping() {
		return bodyDefBean.getAngularDamping();
	}
	public final void setAngularDamping(Float angularDamping) {
		bodyDefBean.setAngularDamping(angularDamping);
	}
	
	public StyleableProperty<Number> gravityScaleProperty() {
		return bodyDefBean.gravityScaleProperty();
	}
	public final Float getGravityScale() {
		return bodyDefBean.getGravityScale();
	}
	public final void setGravityScale(Float gravityScale) {
		bodyDefBean.setGravityScale(gravityScale);
	}
	
	public StyleableProperty<Boolean> allowSleepProperty() {
		return bodyDefBean.allowSleepProperty();
	}
	public final boolean isAllowSleep() {
		return bodyDefBean.isAllowSleep();
	}
	public final void setAllowSleep(boolean allowSleep) {
		bodyDefBean.setAllowSleep(allowSleep);
	}
	
	public StyleableProperty<Boolean> awakeProperty() {
		return bodyDefBean.awakeProperty();
	}
	public final boolean isAwake() {
		return bodyDefBean.isAwake();
	}
	public final void setAwake(boolean awake) {
		bodyDefBean.setAwake(awake);
	}
	
	public StyleableProperty<Boolean> fixedRotationProperty() {
		return bodyDefBean.fixedRotationProperty();
	}
	public final boolean isFixedRotation() {
		return bodyDefBean.isFixedRotation();
	}
	public final void setFixedRotation(boolean fixedRotation) {
		bodyDefBean.setFixedRotation(fixedRotation);
	}
	
	public StyleableProperty<Boolean> activeProperty() {
		return bodyDefBean.activeProperty();
	}
	public final boolean isActive() {
		return bodyDefBean.isActive();
	}
	public final void setActive(boolean active) {
		bodyDefBean.setActive(active);
	}
}
