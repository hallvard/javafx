package games;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class FxmlGame {

	public int randomInt(int min, int max) {
		return min + (int) (Math.random() * (max - min + 1));
	}

	public boolean randomBoolean() {
		return Math.random() < 0.5;
	}
	
	// animation

	public void runDelayed(int delay, Runnable... tasks) {
		runDelayed(new Timer(), delay, new LinkedList<Runnable>(Arrays.asList(tasks)));
	}
	
	private static void runDelayed(Timer timer, int delay, Queue<Runnable> q) {
		timer.schedule(new TimerTask() {
			public void run() {
				Runnable task = q.poll();
				try {
					Platform.runLater(task);
					if (q.isEmpty()) {
						timer.cancel();
					} else {
						runDelayed(timer, delay, q);
					}
				} catch (Exception e) {
				}
			}
		}, delay);
	}
	
	private Timer tickTimer = null;
	private int tickDelay = 0;
	
	private void keepTicking() {
		if (tickTimer == null) {
			return;
		}
		tickTimer.schedule(new TimerTask() {
			public void run() {
				if (Platform.isFxApplicationThread()) {
					tick();
					keepTicking();
				} else {
					Platform.runLater(this);
				}
			}
		}, tickDelay);
	}

	public void setTickDelay(int delay) {
		this.tickDelay = delay;
		if (this.tickDelay == 0 && tickTimer != null) {
			if (tickTimer != null) {
				tickTimer.cancel();
			}
			tickTimer = null;
		} else if (this.tickDelay > 0) {
			if (tickTimer == null) {
				tickTimer = new Timer();
			}
			keepTicking();
		}
	}

	protected void tick() {
	}
	
	//

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

	//

	public static void launch(String... args) {
		FxmlApp.main(args);
	}

	public static void launch(Class<?> clazz, String... args) {
		String[] allArgs = new String[args.length + 1];
		System.arraycopy(args, 0, allArgs, 1, args.length);
		allArgs[0] = "/" + clazz.getName().replace(".", "/") + ".fxml";
		launch(allArgs);
	}
}
