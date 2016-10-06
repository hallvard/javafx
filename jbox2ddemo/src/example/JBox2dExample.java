package example;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import fxmlbox2d.INodeBody;
import fxmlbox2d.css.Body;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class JBox2dExample extends JBox2dApp {
	
	@Override
	protected void handleDirectionKey(int dx, int dy) {
	}
	
	@FXML
	Body paddle;

	@FXML
	Body ball;
	
	@Override
	protected double getScaling() {
		return 40.0;
	}
	
	@Override
	protected World createWorldInstance() {
		return new World(new Vec2());
	}
	
	@Override
	protected void configureWorld() {
		super.configureWorld();
		setSpeed(ball, 0.0, 200.0);
	}

	@Override
	protected void handleCollision(INodeBody<Node> nodeA, INodeBody<Node> nodeB) {
		System.out.println("A: " + nodeA.getBody() + ", B: " + nodeB.getBody());
	}
	@Override
	protected void handleCollision(Node nodeA, Node nodeB) {
		System.out.println("A: " + nodeA + ", B: " + nodeB);
	}

	//

	public static void main(String[] args) {
        launch(JBox2dExample.class, args);
    }
}
