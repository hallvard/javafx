package game;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class FxmlApp extends Application {

	// fxml loading on startup
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		URL url = this.getClass().getResource(this.getClass().getSimpleName() + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        fxmlLoader.setLocation(url);
        Parent root = (Parent) fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
	}

	// images

	public Image createTextImage(Object o, Font font, Paint stroke, Paint fill) {
		Text text = new Text(String.valueOf(o));
		text.setFont(font);
		return createShapeImage(text, stroke, fill, Color.TRANSPARENT);
	}
	
	public Image createShapeImage(Shape shape, Paint stroke, Paint fill, Paint background) {
		shape.setStroke(stroke);
		shape.setFill(fill);
		return createNodeImage(shape, background);
	}
	
	public Image createNodeImage(Node node, Paint background) {
		StackPane pane = new StackPane();
		pane.getChildren().add(node);
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(background);
		WritableImage image = pane.snapshot(params, null);
		return image;
	}
}
