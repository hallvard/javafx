package trinn2;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StickMan1 extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("StickMan1.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

	public static void main(String[] args) {
		launch(args);
	}
}
