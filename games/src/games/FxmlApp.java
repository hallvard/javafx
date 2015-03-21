package games;

import java.net.URL;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxmlApp extends Application {

	// fxml loading on startup
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		List<String> args = getParameters().getRaw();
		String fxmlFileName = this.getClass().getSimpleName() + ".fxml";
		if (args.size() > 0) {
			String arg0String = String.valueOf(args.get(0));
			if (arg0String.endsWith(".fxml")) {
				fxmlFileName = arg0String;
			}
		}
		URL url = this.getClass().getResource(fxmlFileName);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new FxmlBuilderFactory());
        Parent root = (Parent) fxmlLoader.load();
        controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        if (controller instanceof FxmlGame) {
        	scene.setOnKeyPressed((FxmlGame) controller);
        	scene.setOnKeyReleased((FxmlGame) controller);
        	scene.setOnKeyTyped((FxmlGame) controller);
        }
		primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private Object controller;
	
	@Override
	public void stop() throws Exception {
		if (controller instanceof FxmlGame) {
			((FxmlGame) controller).setTickDelay(0);
		}
	}
	
	public static void main(String... args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("Missing Application class name");
		}
		Class<? extends Application> appClass = null;
		String classOrFxml = args[0];
		if (classOrFxml.endsWith(".fxml")) {
			appClass = FxmlApp.class;
		} else {
			Class<?> clazz = null;
			try {
				clazz = Class.forName(classOrFxml);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException(classOrFxml + " is not recognized as a class name");
			}
			if (! (Application.class.isAssignableFrom(clazz))) {
				throw new IllegalArgumentException("Application class must be a subclass of javafx.application.Application: " + clazz);
			}
			appClass = (Class<? extends Application>) clazz;
			// remove class name from args
			String[] restArgs = new String[args.length - 1];
			System.arraycopy(args, 0, restArgs, 0, restArgs.length);
			args = restArgs;
		}
		// launch the application
		launch((Class<? extends Application>) appClass, args);
	}
}
