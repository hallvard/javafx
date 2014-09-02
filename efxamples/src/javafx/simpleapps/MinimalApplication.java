package javafx.simpleapps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MinimalApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Pane root = new Pane(); // Root of the scene graph
        Scene scene = new Scene(root, 500, 500);
     
        stage.setScene(scene);
        stage.setTitle("MinimalApplication");
        stage.show();
    }

	public static void main(String[] args) {
        launch(MinimalApplication.class, args);
    }
}
