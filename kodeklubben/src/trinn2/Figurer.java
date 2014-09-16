package trinn2;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Figurer extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        Parent root = (Parent) fxmlLoader.load(this.getClass().getResourceAsStream("figurer1.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
     
    public static void main(String[] args) {
        launch(args);
    }
}