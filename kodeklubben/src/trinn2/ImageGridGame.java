package trinn2;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class ImageGridGame<T> extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		URL url = this.getClass().getResource(this.getClass().getSimpleName() + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        fxmlLoader.setLocation(url);
        Parent root = (Parent) fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        ensureKeyboardFocus();
	}

	protected void ensureKeyboardFocus() {
		imageGrid.requestFocus();
	}

	@FXML
	protected ImageGrid<T> imageGrid;

	@FXML
	protected void initialize() {
		for (Map.Entry<T, String> entry : imageGrid.getImageKeyMapEntries()) {
			imageGrid.setImage(entry.getKey(), entry.getValue(), this);
		}
	}

	// fields and methods for managing the grid

	private List<T> cells = new ArrayList<T>();
	
	public boolean isValidXY(int x, int y) {
		return x >= 0 && x < imageGrid.getColumnCount() && y >= 0 && y < imageGrid.getRowCount();
	}

	private int cellPos(int x, int y) {
		return y * imageGrid.getColumnCount() + x;
	}

	public T getCell(int x, int y) {
		if (isValidXY(x, y)) {
			int pos = cellPos(x, y);
			return (pos >= 0 && pos < cells.size() ? cells.get(pos) : null);
		}
		return null;
	}

	public void setCell(int x, int y, T value) {
		if (isValidXY(x, y)) {
			int pos = cellPos(x, y);
			while (pos >= cells.size()) {
				cells.add(null);
			}
			cells.set(pos, value);
		}
	}
	
	// iterate over cells
	
	private static interface CellProcedure {
		public void applyCell(int x, int y);
	}
	
	public void foreachCell(CellProcedure proc, int startX, int startY, int width, int height) {
		for (int x = startX; x < (startX + width) && x < imageGrid.getColumnCount(); x++) {
			for (int y = startY; y < (startY + height) && y < imageGrid.getRowCount(); y++) {
				proc.applyCell(x, y);
			}
		}
	}
	public void foreachCell(CellProcedure proc) {
		foreachCell(proc, 0, 0, imageGrid.getColumnCount(), imageGrid.getRowCount());
	}
	protected void updateCell(int x, int y) {
		imageGrid.setImage(getCell(x, y), x, y);
	}

	protected void updateGrid() {
		foreachCell((x, y) -> updateCell(x, y));
	}


	protected void fillCells(T value, int startX, int startY, int width, int height) {
		foreachCell((x, y) -> setCell(x, y, value), startX, startY, width, height);
	}
	protected void fillGrid(T value) {
		fillCells(value, 0, 0, imageGrid.getColumnCount(), imageGrid.getRowCount());
	}

	// count cells
	
	private static interface CellFunction<T> {
		public T applyCell(int x, int y);
	}

	public int countCells(CellFunction<Integer> fun, int startX, int startY, int width, int height) {
		int accum = 0;
		for (int x = startX; x < (startX + width) && x < imageGrid.getColumnCount(); x++) {
			for (int y = startY; y < (startY + height) && y < imageGrid.getRowCount(); y++) {
				accum += fun.applyCell(x, y);
			}
		}
		return accum;
	}
	public int countCells(CellFunction<Integer> fun) {
		return countCells(fun, 0, 0, imageGrid.getColumnCount(), imageGrid.getRowCount());
	}

	//

	@FXML
	protected void keyPressed(KeyEvent keyEvent) {
		KeyCode code = keyEvent.getCode();
		boolean consumed = false;
		consumed = keyPressed(code);
		if (! consumed) {
			if (code == KeyCode.LEFT) {
				consumed = keyPressed(-1,  0);
			} else if (code == KeyCode.RIGHT) {
				consumed = keyPressed( 1,  0);
			} else if (code == KeyCode.UP) {
				consumed = keyPressed( 0, -1);
			} else if (code == KeyCode.DOWN) {
				consumed = keyPressed( 0,  1);
			}
		}
		if (consumed) {
			keyEvent.consume();
		}
	}

	protected boolean keyPressed(KeyCode code) {
		return false;
	}

	protected boolean keyPressed(int dx, int dy) {
		return false;
	}

	@FXML
	void mouseClicked(MouseEvent mouseEvent) {
		Node child = mouseEvent.getPickResult().getIntersectedNode();
		Integer x = GridPane.getColumnIndex(child);
		Integer y = GridPane.getRowIndex(child);
		if (x != null && y != null) {
			mouseClicked(x, y);
		}
	}

	void mouseClicked(int x, int y) {
	}

	//
	
	public int randomInt(int min, int max) {
		return min + (int) (Math.random() * (max - min + 1));
	}
	
	//

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
	private int tickDelay = 500;
	
	private void keepTicking() {
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

	public void startTicking(int delay) {
		if (tickTimer == null) {
			tickTimer = new Timer();
		}
		setTickDelay(delay);
		keepTicking();
	}

	public void stopTicking() {
		if (tickTimer != null) {
			tickTimer.cancel();
		}
		tickTimer = null;
	}
	
	public void setTickDelay(int delay) {
		this.tickDelay = delay;
	}
	
	protected void tick() {
	}
	
	@Override
	public void stop() throws Exception {
		stopTicking();
		super.stop();
	}
}
