package games.simulation;

import javafx.scene.Node;

public class MovingObject {

	private double vx = 0.0, vy = 0.0, ax = 0.0, ay = 0.0;
	private final Node graphics;
	
	public MovingObject(Node graphics) {
		super();
		this.graphics = graphics;
	}

	public void raccelerate(double fx, double fy) {
		vx += vx * fx;
		vy += vy * fy;
	}

	public void setVelocity(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}

	public void accelerate(double ax, double ay) {
		vx += ax;
		vy += ay;
	}
	
	public void accelerate() {
		accelerate(ax, ay);
	}
	
	public void setAcceleration(double ax, double ay) {
		this.ax = ax;
		this.ay = ay;
	}
	
	public void relocate(double dx, double dy) {
		graphics.setLayoutX(graphics.getLayoutX() + dx);
		graphics.setLayoutY(graphics.getLayoutY() + dy);
	}
	
	public void relocate() {
		relocate(vx, vy);
	}

	public void tick() {
		relocate();
		accelerate();
	}
}
