package jfxwithjbox2d;
 
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Circle;
 
/**
 *
 * @author dilip
 */
public class Ball {
 
    //JavaFX UI for ball
    public Node node;
     
    //X and Y position of the ball in JBox2D world
    private float posX, posY;
     
    //Ball radius in pixels
    private int radius;
     
    /**
     * There are three types bodies in JBox2D – Static, Kinematic and dynamic 
     * In this application static bodies (BodyType.STATIC – non movable bodies) 
     * are used for drawing hurdles and dynamic bodies (BodyType.DYNAMIC–movable bodies) 
     * are used for falling balls
     */
    private BodyType bodyType;
 
    //Gradient effects for balls
    private LinearGradient gradient;
     
    public Ball(float posX, float posY){
        this(posX, posY, Utils.BALL_SIZE, BodyType.DYNAMIC,Color.RED);
    }

    public Ball(float posX, float posY, int radius, BodyType bodyType, Color color) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        this.bodyType = bodyType;
        this.gradient = Utils.getBallGradient(color);
        node = create();
    }
     
    /**
     * This method creates a ball by using Circle object from JavaFX and CircleShape from JBox2D
     */
    private Node create(){
        //Create an UI for ball - JavaFX code
        Circle ball = new Circle();
        ball.setRadius(radius);
        ball.setFill(gradient); //set look and feel 
         
        /**
         * Set ball position on JavaFX scene. We need to convert JBox2D coordinates 
         * to JavaFX coordinates which are in pixels.
         */
        ball.setLayoutX(Utils.toPixelPosX(posX)); 
        ball.setLayoutY(Utils.toPixelPosY(posY));
        
        ball.setCache(true); //Cache this object for better performance
         
        // Create an JBox2D body definition for ball.
        BodyDef bd = new BodyDef();
        bd.type = bodyType;
        bd.position.set(posX, posY);
         
        CircleShape cs = new CircleShape();
        cs.m_radius = (Math.abs(Utils.toPosX(radius) - Utils.toPosX(0)) + Math.abs(Utils.toPosY(radius) - Utils.toPosY(0))) / 2;  //We need to convert radius to JBox2D equivalent

        // Create a fixture for ball
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.9f;
        fd.friction = 0.3f;        
        fd.restitution = 0.6f;
 
        /**
        * Virtual invisible JBox2D body of ball. Bodies have velocity and position. 
        * Forces, torques, and impulses can be applied to these bodies.
        */
        Body body = Utils.world.createBody(bd);
        body.createFixture(fd);
        ball.setUserData(body);
        return ball;
    }
}