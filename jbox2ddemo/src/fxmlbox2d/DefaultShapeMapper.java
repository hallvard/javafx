package fxmlbox2d;

import java.util.List;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class DefaultShapeMapper implements INodeMapper<Shape> {

	private GeometryHelper geometryHelper;
	
	public DefaultShapeMapper(GeometryHelper geometryHelper) {
		this.geometryHelper = geometryHelper;
	}
	
	@Override
	public Shape create(Node node) {
		if (node instanceof Rectangle) {
			return mapRectangle((Rectangle) node);
		} else if (node instanceof Polygon) {
			return mapPolygon((Polygon) node);
		} else if (node instanceof Circle) {
			return mapCircle((Circle) node);
		} else if (node instanceof Line) {
			return mapLine((Line) node);
		} else if (node instanceof Polyline) {
			return mapPolyline((Polyline) node);
		}
		return null;
	}

	protected Shape mapPolyline(Polyline node) {
		double[] points = getTranslatedPoints(node.getPoints(), node.getLayoutX(), node.getLayoutY());
		Vec2[] vertices = toVec2(points);
		ChainShape chain = new ChainShape();
		if (vertices[0].equals(vertices[vertices.length - 1])) {
			chain.createLoop(vertices, vertices.length);
		} else {
			chain.createChain(vertices, vertices.length);
		}
		return chain;
	}

	protected Shape mapLine(Line node) {
		EdgeShape edge = new EdgeShape();
		edge.set(geometryHelper.fromStart(node), geometryHelper.fromEnd(node));
		return edge;
	}

	protected Shape mapCircle(Circle node) {
		CircleShape circle = new CircleShape();
		geometryHelper.set2Point(circle.m_p, node.getLayoutX() + node.getCenterX(), node.getLayoutY() + node.getCenterY());
		Point2D radius = geometryHelper.fxVec2world(node.getRadius(), node.getRadius());
		circle.setRadius((float) (Math.abs(radius.getX()) + Math.abs(radius.getY())) / 2);
		return circle;
	}

	protected double[] getTranslatedPoints(List<? extends Number> nodePoints, double dx, double dy) {
		double[] points = new double[nodePoints.size()];
		for (int i = 0; i < points.length; i++) {
			points[i] = nodePoints.get(i).doubleValue();
		}
		translate(points, dx, dy);
		return points;
	}
	
	protected void translate(double[] points, double dx, double dy) {
		for (int i = 0; i < points.length; i += 2) {
			points[i] += dx;
			points[i + 1] += dy;
		}		
	}

	protected Vec2[] toVec2(double...points) {
		Vec2[] vertices = new Vec2[points.length / 2];
		for (int i = 0; i < points.length; i += 2) {
			vertices[i / 2] = new Vec2((float) points[i], (float) points[i + 1]);
		}
		return vertices;
	}

	protected Shape mapPolygon(Polygon node) {
		double[] points = getTranslatedPoints(node.getPoints(), node.getLayoutX(), node.getLayoutY());
		Vec2[] vertices = toVec2(points);
		PolygonShape polygon = new PolygonShape();
		polygon.set(vertices, vertices.length);
		return polygon;
	}

	protected Shape mapRectangle(Rectangle node) {
		PolygonShape polygon = new PolygonShape();
		double w = node.getWidth(), h = node.getHeight();
		double[] corners = new double[] { 0, h, w, h, w, 0, 0, 0 };
		double rotate = node.getRotate();
		Rotate rt = (rotate != 0.0 ? Transform.rotate(rotate, w / 2, h / 2) : null);
		for (int i = 0; i < corners.length; i += 2) {
			if (rt != null) {
				Point2D transformed = rt.transform(corners[i], corners[i + 1]);
				corners[i] = transformed.getX();
				corners[i + 1] = transformed.getY();
			}
		}
		double dx = node.getLayoutX() + node.getX(), dy = node.getLayoutY() + node.getY();
		translate(corners, dx, dy);
		Vec2[] vertices = new Vec2[4];
		geometryHelper.set2Points(vertices, corners);
		polygon.set(vertices, 4);
		return polygon;
	}
}
