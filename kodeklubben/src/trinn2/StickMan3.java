package trinn2;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class StickMan3 extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        Parent root = (Parent) fxmlLoader.load(this.getClass().getResourceAsStream("StickMan3.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML Pane stickManPane;
    @FXML Node stickMan, armsAndLegs1, armsAndLegs2;
    @FXML Shape leftEye, rightEye;
    
    boolean step = true;
    KeyCode direction = KeyCode.DOWN;
    
    @FXML
    void initialize() {
    	stickManPane.requestFocus();
    	update();
    }

    void update() {
    	if (direction == KeyCode.LEFT) {
        	leftEye.setVisible(true);
        	rightEye.setVisible(false);
    	} else if (direction == KeyCode.RIGHT) {
    		leftEye.setVisible(false);
    		rightEye.setVisible(true);
	    } else {
	    	leftEye.setVisible(true);
	    	rightEye.setVisible(true);
	    }
    	if (step) {
    		leftEye.setFill(Color.BLUE);
    		rightEye.setFill(Color.BLUE);
    	} else {
    		leftEye.setFill(Color.WHITE);
    		rightEye.setFill(Color.WHITE);
    	}
    	armsAndLegs1.setVisible(step);
    	armsAndLegs2.setVisible(! step);    	
    }
    
    @FXML
    void keyPressed(KeyEvent keyEvent) {
    	step = ! step;
    	if (keyEvent.getCode() == KeyCode.LEFT) {
    		stickMan.setLayoutX(stickMan.getLayoutX() - 5);
    	} else if (keyEvent.getCode() == KeyCode.RIGHT) {
    		stickMan.setLayoutX(stickMan.getLayoutX() + 5);
	    } else if (keyEvent.getCode() == KeyCode.DOWN) {
	    	stickMan.setLayoutY(stickMan.getLayoutY() + 5);
	    } else if (keyEvent.getCode() == KeyCode.UP) {
	    	stickMan.setLayoutY(stickMan.getLayoutY() - 5);
	    } else {
	    	return;
	    }
    	direction = keyEvent.getCode();
    	update();
    }

	public static void main(String[] args) {
		launch(args);
	}
}
