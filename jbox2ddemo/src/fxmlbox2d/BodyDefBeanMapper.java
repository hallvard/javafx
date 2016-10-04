package fxmlbox2d;

import org.jbox2d.dynamics.BodyDef;

import fxmlbox2d.css.BodyDefBean;
import fxmlbox2d.css.BodyDefBeanOwner;
import javafx.scene.Node;

public class BodyDefBeanMapper implements INodeMapper<BodyDef> {

	public BodyDefBeanMapper() {
	}

	@Override
	public BodyDef create(Node node) {
		if (node instanceof BodyDefBeanOwner) {
			BodyDefBean<?> bodyDefBean = ((BodyDefBeanOwner) node).getBodyDefBean();
			BodyDef bodyDef = bodyDefBean.createBodyDef();
			return bodyDef;
		}
		return null;
	}
}
