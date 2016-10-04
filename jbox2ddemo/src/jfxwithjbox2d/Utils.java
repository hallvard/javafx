package jfxwithjbox2d;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
 
/**
 *
 * @author dilip
 */
public class Utils {
    //Create a JBox2D world. 
    public static final World world = new World(new Vec2(0.0f, -10.0f));
     
    //Screen width and height
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    
    public static final float SCALING = 100.0f;
     
    //Ball radius in pixel
    public static final int BALL_SIZE = 8;
     
    //Total number of balls
    public final static int NO_OF_BALLS = 1000; 
     
    //Ball gradient
    private final static LinearGradient BALL_GRADIENT = new LinearGradient(0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.RED)});
     
    //This method adds a ground to the screen. 
    public static void addGround(float width, float height){
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width,height);
         
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
 
        BodyDef bd = new BodyDef();
        bd.position= new Vec2(0.0f,-10f);
 
        world.createBody(bd).createFixture(fd);
    }
     
    //This method creates a walls. 
    public static void addWall(float posX, float posY, float width, float height){
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width,height);
         
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 1.0f;
        fd.friction = 0.3f;    
 
        BodyDef bd = new BodyDef();
        bd.position.set(posX, posY);
         
        Utils.world.createBody(bd).createFixture(fd);
    }
     
    //This gives a look and feel to balls
    public static LinearGradient getBallGradient(Color color){
        if(color.equals(Color.RED))
            return BALL_GRADIENT;
        else
            return new LinearGradient(0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0, Color.WHITE), new Stop(1, color)});
    }

    
    // Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
    public static float toPixelPosX(float posX) {
        float x = WIDTH*posX / SCALING;
        return x;
    }
 
    //Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
    public static float toPosX(float posX) {
        float x =   (posX*SCALING)/WIDTH;
        return x;
    }
     
    //Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
    public static float toPixelPosY(float posY) {
        float y = HEIGHT - (1.0f*HEIGHT) * posY / SCALING;
        return y;
    }
     
    //Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
    public static float toPosY(float posY) {
        float y = SCALING - ((posY * SCALING) /HEIGHT) ;
        return y;
    }

    //Convert a JBox2D width to pixel width
    public static float toPixelWidth(float width) {
        return WIDTH*width / SCALING;
    }
     
    //Convert a JBox2D height to pixel height
    public static float toPixelHeight(float height) {
        return HEIGHT*height/SCALING;
    }
  
}