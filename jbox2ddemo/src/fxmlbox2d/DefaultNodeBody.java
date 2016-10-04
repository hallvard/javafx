package fxmlbox2d;

import java.util.Map;

import org.jbox2d.dynamics.Body;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.transform.Transform;

public class DefaultNodeBody<N extends Node> extends Pair<Body, N> implements INodeBody<N> {

	public DefaultNodeBody(N node, Body body) {
		super(body, node);
	}

	@Override
	public N getNode() {
		return getValue();
	}

	@Override
	public Body getBody() {
		return getKey();
	}

	@Override
	public void updateNode(GeometryHelper helper) {
		Point2D nodePosition = helper.world2fx(getBody().getPosition().x, getBody().getPosition().y, getNode().getParent());
		getNode().setLayoutX(nodePosition.getX());
		getNode().setLayoutY(nodePosition.getY());
		double fxAngle = (- getBody().getAngle() * 180 / Math.PI) % 360;
		getNode().setRotate(fxAngle);
	}
}
