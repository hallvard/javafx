package trinn2;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class StickMan2 extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        Parent root = (Parent) fxmlLoader.load(this.getClass().getResourceAsStream("StickMan2.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML Node stickMan, armsAndLegs1, armsAndLegs2;
    @FXML Shape eye;
    
    boolean step = true;

    @FXML
    void initialize() {
    	update();
    }

    void update() {
    	if (step) {
    		eye.setFill(Color.BLUE);
    	} else {
    		eye.setFill(Color.WHITE);
    	}
    	armsAndLegs1.setVisible(step);
    	armsAndLegs2.setVisible(! step);
    }

    @FXML
    void walk() {
    	step = ! step;
    	stickMan.setLayoutX(stickMan.getLayoutX() + 5);
    	update();
    }

	public static void main(String[] args) {
		launch(args);
	}
}
