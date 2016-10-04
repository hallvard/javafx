package example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fxmlbox2d.BodyDefBeanMapper;
import fxmlbox2d.DefaultNodeBody;
import fxmlbox2d.DefaultShapeMapper;
import fxmlbox2d.FixtureDefBeanMapper;
import fxmlbox2d.GeometryHelper;
import fxmlbox2d.INodeBody;
import fxmlbox2d.INodeMapper;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jbox2dutil.DebugDrawJavaFX;

public class Box2d1 extends Application {
	
    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = start((Scene) null);
        scene.setOnKeyPressed(this::handleKey);
		primaryStage.setScene(scene);
        primaryStage.show();
        initializeAnimation();
    }
    
    @Override
    public void stop() throws Exception {
    	worldTimer.stop();
    	worldStepper = null;
    	world = null;
    	super.stop();
    }
    
    public Scene start(Scene scene) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(this.getClass().getSimpleName() + ".fxml"));
    	fxmlLoader.setController(this);
    	Parent root = (Parent) fxmlLoader.load();
    	if (scene == null) {
    		scene = new Scene(root);
    	} else {
    		scene.setRoot(root);
    	}
		initializeWorld();
		updateBodyNodes();
		dumpGeometry();
    	return scene;
    }

    private void initializeAnimation() {
		worldTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (last >= 0) {
					long milliseconds = (now - last) / 1_000_000;
					if (milliseconds > 1000.0 / 60.0 && worldStepper != null && world != null) {
						worldStepper.accept(((float) milliseconds) / 1000.0f);
						last = now;
					}
				} else {
					last = now;
				}
			}
		};
    	worldStepper = (t) -> {
			world.step(t, 8, 3);
			updateBodyNodes();
			updateCanvas();
//			dumpGeometry();
			return;
		};
    }

	@FXML
    private void initialize() {
    	last = -1;
    	world = null;
    }
    
	private void handleKey(KeyEvent keyEvent) {
    	switch (keyEvent.getCode()) {
    	case ESCAPE: {
    		try {
    			if (worldTimer != null) {
    	    		worldTimer.stop();
    			}
				start(worldParent.getScene());
			} catch (IOException e) {
			}
    		break;
    	}
    	case SPACE: {
    		toggleStep();
    		break;
    	}
    	default:
    	}
    }
    
	private AnimationTimer worldTimer;
    private Consumer<Float> worldStepper;

    private long last;
    
    private void toggleStep() {
    	if (last >= 0) {
    		worldTimer.stop();
    		last = -1;
    	} else {
    		if (world == null) {
    			initializeWorld();
    		}
			worldTimer.start();
		}
	}

    @FXML
    private Pane worldParent;
    
//    private IViewportTransform viewportTransform;
    private GeometryHelper geometryHelper;

    private INodeMapper<BodyDef> nodeBodyMapper;
    private INodeMapper<FixtureDef> nodeFixtureMapper;
    private INodeMapper<Shape> nodeShapeMapper;
    private Map<Body, INodeBody<Node>> bodyNodeMapping;
    private World world;

    private DebugDrawJavaFX debugDraw;
    private Canvas overlay;

    protected void initializeWorld() {
    	
    	geometryHelper = new GeometryHelper(worldParent, 1.0);
    	nodeBodyMapper = new BodyDefBeanMapper();
    	nodeFixtureMapper = new FixtureDefBeanMapper();
    	nodeShapeMapper = new DefaultShapeMapper(geometryHelper);
    	createWorld();

    	overlay = new Canvas();
    	overlay.setWidth(worldParent.getPrefWidth());
    	overlay.setHeight(worldParent.getPrefHeight());
    	
    	debugDraw = new DebugDrawJavaFX(overlay);
    	debugDraw.setFlags(DebugDraw.e_shapeBit | DebugDraw.e_wireframeDrawingBit);
    	debugDraw.setViewportTransform(geometryHelper.getViewportTransform());
    	
    	worldParent.getChildren().add(overlay);
    	world.setDebugDraw(debugDraw);
    }

    private void updateCanvas() {
    	GraphicsContext gc = overlay.getGraphicsContext2D();
    	Bounds bounds = overlay.getBoundsInLocal();
    	gc.clearRect(bounds.getMinX(), bounds.getMinX(), bounds.getWidth(), bounds.getHeight());
        world.drawDebugData();
	}

    private void dumpGeometry() {
    	int num = 1;
    	for (Body body : bodyNodeMapping.keySet()) {
    		System.out.println(String.format("#%d: %f,%f /%f", num, body.getPosition().x, body.getPosition().y, body.getAngle()));
    		num++;
    	}
    }

	protected void updateBodyNodes() {
		for (Body body : bodyNodeMapping.keySet()) {
			INodeBody<?> nodeBody = bodyNodeMapping.get(body);
			nodeBody.updateNode(geometryHelper);
		}
	}

	private void createWorld() {
    	world = new World(new Vec2(0.0f, -20.0f));
		bodyNodeMapping = new HashMap<>();
		Collection<Node> fixtureNodes = new ArrayList<>();
		for (Node child : worldParent.getChildrenUnmodifiable()) {
			BodyDef bodyDef = nodeBodyMapper.create(child);
			if (bodyDef != null) {
				configureBody(child, bodyDef);
			} else if (nodeFixtureMapper.create(child) != null) {
				fixtureNodes.add(child);
			}
		}
		for (Node child : fixtureNodes) {
			fxmlbox2d.css.Body bodyNode = new fxmlbox2d.css.Body();
			bodyNode.getChildren().add(child);
			worldParent.getChildren().add(bodyNode);
			BodyDef bodyDef = nodeBodyMapper.create(bodyNode);
			configureBody(bodyNode, bodyDef);
		}
	}

	private void configureBody(Node child, BodyDef bodyDef) {
		Bounds bounds = child.getBoundsInLocal();
		double cx = (bounds.getMinX() + bounds.getMaxX()) / 2, cy = (bounds.getMinY() + bounds.getMaxY()) / 2;
		double childX = child.getLayoutX() + cx, childY = child.getLayoutY() + cy;
		child.setLayoutX(childX);
		child.setLayoutY(childY);
		Point2D bodyPosition = geometryHelper.fxPoint2world(childX, childY, child.getParent());
		bodyDef.getPosition().set((float) bodyPosition.getX(), (float) bodyPosition.getY());
		Body body = world.createBody(bodyDef);
		if (bodyDef.getType() != BodyType.STATIC) {
			bodyNodeMapping.put(body, new DefaultNodeBody<Node>(child, body));
		}
		if (child instanceof Parent) {
			Iterable<Node> bodyChildren = ((Parent) child).getChildrenUnmodifiable();
			int fixtureCount = 0;
			for (Node bodyChild : bodyChildren) {
				bodyChild.setLayoutX(bodyChild.getLayoutX() -cx);
				bodyChild.setLayoutY(bodyChild.getLayoutY() -cy);
				FixtureDef fixtureDef = nodeFixtureMapper.create(bodyChild);
				if (fixtureDef != null) {
					Shape shape = nodeShapeMapper.create(bodyChild);
					if (shape != null) {
						fixtureDef.setShape(shape);
						body.createFixture(fixtureDef);
						fixtureCount++;
					}
				}
			}
			if (fixtureCount > 0) {
			} else {
				world.destroyBody(body);
			}
		}
	}

	public static void main(String[] args) {
        launch(Box2d1.class, args);
    }
}
