package fxmlbox2d;

import java.util.List;

import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Mat22;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

public class GeometryHelper {

	private Region world;
	
	private IViewportTransform viewportTransform;
	
	public GeometryHelper(Region world, double scale) {
		this.world = world;
		OBBViewportTransform obb = new OBBViewportTransform();
		obb.setTransform(new Mat22((float) scale, 0.0f, 0.0f, (float) scale));
		obb.setCenter((float) (world.getWidth() / 2), (float) (world.getHeight() / 2));
		obb.setExtents((float) (world.getWidth() / 2), (float) (world.getHeight() / 2));
		obb.setYFlip(true);
		this.viewportTransform = obb;
	}

	public IViewportTransform getViewportTransform() {
		return viewportTransform;
	}

	private Vec2 fx2worldTemp1 = new Vec2(), fx2worldTemp2 = new Vec2();
	
	public Point2D fxPoint2world(double x, double y, Parent context) {
		while (context != null && context != world) {
			Bounds bounds = context.getBoundsInParent();
			x += bounds.getMinX();
			y += bounds.getMinY();
			context = context.getParent();
		}
		Vec2 result = fx2worldTemp1;
		fx2worldTemp2.set((float) x, (float) y);
		viewportTransform.getScreenToWorld(fx2worldTemp2, result);
		return new Point2D(result.x, result.y);
	}
	
	public Point2D fxVec2world(double x, double y) {
		Vec2 result = fx2worldTemp1;
		fx2worldTemp2.set((float) x, (float) y);
		viewportTransform.getScreenVectorToWorld(fx2worldTemp2, result);
		return new Point2D(result.x, result.y);
	}

	private Vec2 world2fxTemp1 = new Vec2(), world2fxTemp2 = new Vec2();
	
	public Point2D world2fx(double x, double y, Node context) {
		Vec2 result = world2fxTemp1;
		world2fxTemp2.set((float) x, (float) y);
		viewportTransform.getWorldToScreen(new Vec2((float) x, (float) y), result);
		return new Point2D(result.x, result.y);
	}

	//

	public Vec2 set2Point(Vec2 vec, double x, double y) {
		Point2D result = fxVec2world(x, y);
		vec.set((float) result.getX(), (float) result.getY());
		return vec;
	}
	
	public Vec2 set2Start(Vec2 vec, Line line) {
		Point2D result = fxVec2world(line.getStartX(), line.getStartX());
		vec.set((float) result.getX(), (float) result.getY());
		return vec;
	}
	public Vec2 fromStart(Line line) {
		return set2Start(new Vec2(), line);
	}

	public Vec2 set2End(Vec2 vec, Line line) {
		Point2D result = fxVec2world(line.getStartX(), line.getStartY());
		vec.set((float) result.getX(), (float) result.getY());
		return vec;
	}
	public Vec2 fromEnd(Line line) {
		return set2End(new Vec2(), line);
	}
	
	public Vec2[] set2Points(Vec2[] vertices, double... points) {
		for (int i = 0; i < points.length; i += 2) {
			Point2D result = fxVec2world(points[i], points[i + 1]);
			vertices[i / 2] = new Vec2((float) result.getX(), (float) result.getY());
		}
		return vertices;
	}

	public Vec2[] set2Points(Vec2[] vertices, List<? extends Number> points) {
		for (int i = 0; i < points.size(); i += 2) {
			Point2D result = fxVec2world(points.get(i).doubleValue(), points.get(i + 1).doubleValue());
			vertices[i / 2] = new Vec2((float) result.getX(), (float) result.getY());
		}
		return vertices;
	}
}
