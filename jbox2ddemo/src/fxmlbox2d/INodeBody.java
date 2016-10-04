package fxmlbox2d;

import org.jbox2d.dynamics.Body;

import javafx.scene.Node;

public interface INodeBody<N extends Node> {
	
	public N getNode();
	public Body getBody();
	
	public void updateNode(GeometryHelper helper);
}
