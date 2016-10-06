package example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

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

public abstract class JBox2dApp extends Application {
	
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
    	case BACK_SPACE: {
    		toggleStep();
    		break;
    	}
    	case LEFT: handleDirectionKey(-1, 0); break;
    	case RIGHT: handleDirectionKey(1, 0); break;
    	case UP: handleDirectionKey(0, -1); break;
    	case DOWN: handleDirectionKey(0, 1); break;
    	default:
    	}
    }
    
	protected void handleDirectionKey(int dx, int dy) {
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
    	
    	geometryHelper = new GeometryHelper(worldParent, getScaling());
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
    	
    	world.setContactListener(new ContactListener() {
    		@Override public void endContact(Contact contact) {}
    		@Override public void preSolve(Contact contact, Manifold oldManifold) {}
    		@Override public void postSolve(Contact contact, ContactImpulse impulse) {}

    		@Override
			public void beginContact(Contact contact) {
				INodeBody<Node> nodeA = bodyNodeMapping.get(contact.getFixtureA().getBody()), nodeB = bodyNodeMapping.get(contact.getFixtureB().getBody());
				handleCollision(nodeA, nodeB);
			}
		});
    }

	protected double getScaling() {
		return 1.0;
	}

    protected void handleCollision(INodeBody<Node> nodeA, INodeBody<Node> nodeB) {
    	if (nodeA != null && nodeB != null) {
    		handleCollision(nodeA.getNode(), nodeB.getNode());
    	}
	}

    protected void handleCollision(Node nodeA, Node nodeB) {
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
			if (body.getType() != BodyType.STATIC) {
				INodeBody<?> nodeBody = bodyNodeMapping.get(body);
				nodeBody.updateNode(geometryHelper);
			}
		}
	}

	private void createWorld() {
    	world = createWorldInstance();
		configureWorld();
	}

	protected void configureWorld() {
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

	protected World createWorldInstance() {
		return new World(new Vec2(0.0f, -50.0f));
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
//		if (bodyDef.getType() != BodyType.STATIC) {
			bodyNodeMapping.put(body, new DefaultNodeBody<Node>(child, body));
//		}
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
	
	// world mutation
	
	private Body getBody(Node node) {
		for (INodeBody<Node> nodeBody : bodyNodeMapping.values()) {
			if (nodeBody.getNode() == node) {
				return getBody(nodeBody);
			}
		}
		return null;
	}

	private Body getBody(INodeBody<Node> nodeBody) {
		return nodeBody.getBody();
	}
	
	protected void setSpeed(Node node, double vx, double vy) {
		Point2D vec = geometryHelper.fxVec2world(vx, vy);
		getBody(node).setLinearVelocity(new Vec2((float) vec.getX(), (float) vec.getY()));
	}

	protected void applyForce(Node node, double fx, double fy) {
		Point2D vec = geometryHelper.fxVec2world(fx, fy);
		getBody(node).applyForceToCenter(new Vec2((float) vec.getX(), (float) vec.getY()));
	}

	//

	public static void main(String[] args) {
		launch(JBox2dApp.class, args);
	}
}
