package trinn2;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Strekmann extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Strekmann.fxml"));
        fxmlLoader.setController(this);
        Parent root = (Parent) fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    @FXML
    Button trykkHerKnapp;
    @FXML
    Polygon kniv;

    @FXML
    void handleTrykkHer() {
    	double layoutX = kniv.getLayoutX() - 10;
		kniv.setLayoutX(layoutX);
		String melding = "Bom!";
		if (layoutX > 50 && layoutX < 80) {
			melding = "Treff!";
		}
		trykkHerKnapp.setText(melding);
    }

	public static void main(String[] args) {
		launch(args);
	}
}
