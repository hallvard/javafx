package fxmlbox2d.css;

import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.shape.Polyline;

public class Border extends Polyline implements FixtureDefBeanOwner {

	private static final StyleablePropertyFactory<Border> SPF = new StyleablePropertyFactory<>(Polyline.getClassCssMetaData());

	private FixtureDefBean<Border> fixtureDefBean;
	
	public Border() {
		getStyleClass().add("border");
		this.fixtureDefBean = new FixtureDefBean<Border>(this, SPF);
	}

	@Override
	public FixtureDefBean<? extends Styleable> getFixtureDefBean() {
		return fixtureDefBean;
	}

	@Override
	public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
		return SPF.getCssMetaData();
	}

	public ObservableValue<Number> densityProperty() {
		return (ObservableValue<Number>) this.fixtureDefBean.densityProperty();
	}
	public final Float getDensity() {
		return this.fixtureDefBean.densityProperty().getValue().floatValue();
	}
	public final void setDensity(Float density) {
		this.fixtureDefBean.densityProperty().setValue(density);
	}

	public ObservableValue<Number> frictionProperty() {
		return (ObservableValue<Number>) this.fixtureDefBean.frictionProperty();
	}
	public final Float getFriction() {
		return this.fixtureDefBean.getFriction();
	}
	public final void setFriction(Float friction) {
		this.fixtureDefBean.setFriction(friction);
	}
	
	public ObservableValue<Number> restitutionProperty() {
		return (ObservableValue<Number>) this.fixtureDefBean.restitutionProperty();
	}
	public final Float getRestitution() {
		return this.fixtureDefBean.getRestitution();
	}
	public final void setRestitution(Float restitution) {
		this.fixtureDefBean.setRestitution(restitution);
	}

	public ObservableValue<Boolean> sensorProperty() {
		return (ObservableValue<Boolean>) this.fixtureDefBean.sensorProperty();
	}
	public final boolean isSensor() {
		return this.fixtureDefBean.isSensor();
	}
	public final void setSensor(boolean sensor) {
		this.fixtureDefBean.setSensor(sensor);
	}
}
