package trinn1;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Example2 extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        Parent root = (Parent) fxmlLoader.load(this.getClass().getResourceAsStream("Example2.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
 
    @FXML
    private TextField textField;
 
    @FXML
    public void handleUpcaseAction(ActionEvent event) {
        textField.setText(textField.getText().toUpperCase());
    }
     
    public static void main(String[] args) {
        launch(args);
    }
}