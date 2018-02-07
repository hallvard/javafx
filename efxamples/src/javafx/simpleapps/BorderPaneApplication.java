package javafx.simpleapps;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BorderPaneApplication extends Application {

	private Text centerText;
	private TextField textField;
	
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane(); // Root of the scene graph
		
		textField = new TextField("center");		
		root.setTop(textField);
		root.setBottom(new Text("bottom"));
		root.setLeft(new Text("left"));
		root.setRight(new Text("right"));
		
		centerText = new Text(200, 200, textField.getText());
		
		Pane shapesPane = new Pane();
		shapesPane.setPrefSize(300, 300);
		Line line = new Line(10, 200, 100, 100); // x1, y1, x2, y2
		line.getStrokeDashArray().setAll(10.0d, 10.0d); // dashes
		Rectangle rect = new Rectangle(150, 10, 30, 40); // x, y, w, h
		rect.setFill(Color.BLUE);
		Ellipse ell = new Ellipse(40, 180, 40, 30); // cx, cy, rx, ry
		ell.setStroke(Color.RED);
		ell.setStrokeWidth(5);
		ell.setFill(Color.GREEN);

		List<String> fonts = Font.getFamilies();
		centerText.setFont(new Font(fonts.get((int) (Math.random() * fonts.size())), 32));
		shapesPane.getChildren().addAll(line, rect, ell, centerText);
		root.setCenter(shapesPane);		
        Scene scene = new Scene(root, 500, 500);
     
        stage.setScene(scene);
        stage.setTitle("BorderPaneApplication");
        stage.show();
    }

	public static void main(String[] args) {
        launch(BorderPaneApplication.class, args);
    }
}
