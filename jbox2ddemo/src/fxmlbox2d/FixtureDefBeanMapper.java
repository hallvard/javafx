package fxmlbox2d;

import org.jbox2d.dynamics.FixtureDef;

import fxmlbox2d.css.FixtureDefBean;
import fxmlbox2d.css.FixtureDefBeanOwner;
import javafx.scene.Node;

public class FixtureDefBeanMapper implements INodeMapper<FixtureDef> {

	@Override
	public FixtureDef create(Node node) {
		if (node instanceof FixtureDefBeanOwner) {
			FixtureDefBean<?> fixtureDefBean = ((FixtureDefBeanOwner) node).getFixtureDefBean();
			return fixtureDefBean.createFixtureDef();
		}
		return null;
	}
}
