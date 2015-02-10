package games.blindrabbit;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BlindRabbit extends Application {

	private Text statusText;

	private TilePane tilePane;
	private List<Text> tiles;

	private int rows = 3, columns = 3; 

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		statusText = new Text();
		Button startButton = new Button("Start");
		startButton.setOnAction((actionEvent) -> { startGame(); });
		root.setTop(startButton);
		root.setBottom(statusText);
		tilePane = new TilePane();
		tilePane.setPrefColumns(columns);
		tilePane.setPrefRows(rows);
		tilePane.setFocusTraversable(true);
		tilePane.setOnKeyPressed((keyEvent) -> { keyPressed(keyEvent); });
		tiles = new ArrayList<Text>();
		Font font = Font.font("Arial", 64);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				Text tile = new Text("");
				tile.setFont(font);
				StackPane tileParent = new StackPane(tile);
				tileParent.setPrefSize(80, 80);
				StackPane.setAlignment(tile, Pos.CENTER);
				tiles.add(tile);
				tilePane.getChildren().add(tileParent);
			}
		}
		root.setCenter(tilePane);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	private int rabbitX, rabbitY;
	private int rabbitDx = 0, rabbitDy = 0;

	private int timeBetweenMoves = 1000;       // Start with 1 second between moves (jumps).
	private int numberOfMoves = 0;                 // Counter for no of moves made.
	private final int movesToWin = 10;

	public int getDelay() {
		return timeBetweenMoves;
	}

	private Timer timer;

	private void startGame() {
		timeBetweenMoves = 1000;
		restartGame();
	}

	private void restartGame() {
		tilePane.requestFocus();
		initRabbit();
		numberOfMoves = 0;
		initTimer();
	}

	private void initTimer() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				if (Platform.isFxApplicationThread()) {
					if (numberOfMoves == 0) {
						fillGrid("");
					}
					tick();
				} else {
					Platform.runLater(this);
				}
			}
		}, 1000, getDelay());
	}

	private void initRabbit() {
		rabbitDx = 0;
		rabbitDy = 0;
		int corner = (int) (Math.random() * 4);
		rabbitX = (corner % 2 == 0 ? 0 : 2);
		rabbitY = (corner >= 2 ? 0 : 2);
		boolean isDx = Math.random() > 0.5d;
		if (isDx) {
			rabbitDx = (corner % 2 == 0 ? 1 : -1);
		} else {
			rabbitDy = (corner >= 2 ? 1 : -1);
		}
		moveRabbitTo(rabbitX, rabbitY);
	}

	private String happyRabbit = "☺", sadRabbit = "☹"; 

	private void moveRabbitTo(int x, int y) {
		setTile("");
		rabbitX = x;
		rabbitY = y;
		setTile(happyRabbit);
	}

	private void setTile(String text) {
		tiles.get(rabbitY * rows + rabbitX).setText(text);
	}

	private boolean isValidPosition(int x, int y) {
		return (x >= 0) && (x < columns) && (y >= 0) && (y < rows);
	}

	private void keyPressed(KeyEvent keyEvent) {
		KeyCode code = keyEvent.getCode();
		if (code == KeyCode.LEFT) {
			rabbitDx = -1;
			rabbitDy = 0;
		} else if (code == KeyCode.RIGHT) {
			rabbitDx = 1;
			rabbitDy = 0;
		} else if (code == KeyCode.UP) {
			rabbitDx = 0;
			rabbitDy = -1;
		} else if (code == KeyCode.DOWN) {
			rabbitDx = 0;
			rabbitDy = 1;
		} else {
			return;
		}
		keyEvent.consume();
	}

	private void fillGrid(String text) {
		for (Text tile : tiles) {
			tile.setText(text);
		}
	}
	
	private void doDie() {
		timer.cancel();
		fillGrid(sadRabbit);
	}

	private void doWin() {
		timer.cancel();
		fillGrid(happyRabbit);
	}

	private void tick() {
		int nextRabbitX = rabbitX + rabbitDx ,nextRabbitY = rabbitY + rabbitDy; 
		if (isValidPosition(nextRabbitX, nextRabbitY)) {   // If it will end up outside the board
			moveRabbitTo(nextRabbitX, nextRabbitY);
			numberOfMoves++;                  // Count this as a move.
			if (numberOfMoves > movesToWin) {    // If enough moves have been done (10):
				doWin();                        // Indicate success
				timeBetweenMoves = timeBetweenMoves * 3 / 4; // New level with 25% less delay.
				restartGame();                  // Restart
			}
		} else {                   // Else: the rabbit will still be on the board
			doDie();                              // Blink all LEDs to indicate failure.
			restartGame();                        // Restart at same speed (level).
		}
		updateStatusText();
	}

	private void updateStatusText() {
		statusText.setText(String.format("Moves left: %d (delay: %d)", (movesToWin - numberOfMoves), getDelay()));
	}

	//

	public static void main(String[] args) {
		launch(BlindRabbit.class);
	}
}
