package games.battleship.battleship3;

import games.IUpdateable;
import games.PersistableController;
import games.imagegrid.GridListener;
import games.imagegrid.ImageGridGame;
import games.imagegrid.ObservableGrid;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class BattleshipFX extends ImageGridGame<String> implements IUpdateable, GridListener {

	@FXML private TextField player1LevelTextField;
	@FXML private TextField player2LevelTextField;

	private TextField[] playerLevelTextFields;
	
	@FXML
	private Text messageText;

    @FXML
    private IBattleshipGame game;

	@FXML private IBattleship playerBoard;
	@FXML private IEnemy enemy;

	private IBattleship[] battleships;

	@FXML
	private PersistableController persistableController;

	@FXML
	protected void initialize() {
		super.initialize();

		persistableController.setStateStore(game);
		persistableController.setUpdate(this);

		playerLevelTextFields = new TextField[]{player1LevelTextField, player2LevelTextField};
		battleships = new IBattleship[]{playerBoard, enemy};
	}

	private int player = 0, winner = -1; // 0 or 1, index into arrays
	private String[] responses = {", you're up!", ", it's time to show who's boss.", ", fire at will!", ", don't just sit there.", ", you know what to do."};

	private String randomResponse() {
		return responses[(int) (Math.random() * responses.length)];
	}

	@FXML
	private void startGame() {
        fillGrid(null);
		int size = 0;
		for (int player = 0; player < battleships.length; player++) {
			String levelText = playerLevelTextFields[player].getText();
			battleships[player].init(levelText);
			if (size == 0) {
				size = battleships[player].getSize();
			} else if (battleships[player].getSize() != size) {
				throw new IllegalArgumentException("The level sizes are not the same!");
			}
		}
        game.init(playerBoard, enemy);
		imageGrid.setDimensions(size, size);
		updateCells();
		updateState("Game started. It is Player " + player + "'s turn.");
	}

	private void updateCell(int x, int y) {
		char cellChar = IBattleship.CELL_OCEAN;
		if (battleships[player].isCellHit(x, y)) {
			cellChar =  (battleships[player].getCellShip(x, y) == null) ? IBattleship.CELL_EMPTY : IBattleship.CELL_SHIP_HIT;
		}
		setCell(x, y, Character.toString(cellChar));
	}

	private void updateCells() {
		foreachCell(this::updateCell);
	}

	@Override
	protected boolean mouseClicked(int x, int y) {
        if (winner < 0) {
            IBattleship battleship = battleships[0];
            Boolean hit = battleship.fire(x, y);
            update(battleship, hit, x, y);
            runDelayed(1500, () -> {
                updateCells();
                enemyFire();
            });
        }
        return true;
    }

    private void enemyFire() {
        GridLocation gridLocation = enemy.target();
        int x = gridLocation.getX();
        int y = gridLocation.getY();
        Boolean hit = enemy.fire(x, y);
        update(enemy, hit, x, y);
        runDelayed(1500, this::updateCells);
    }

	public static void main(String[] args) {
		launch(BattleshipFX.class);
	}

	@Override
	public void updateState(boolean fullUpdate) {}

	public void updateState(int x1, int y1, int x2, int y2) {
		updateState(true);
	}

	@Override
	public void updateState(String text) {
		messageText.setText(text);
	}

    @Override
    public void gridChanged(ObservableGrid grid, int x, int y, int w, int h) {
        updateCell(x, y);
    }

    private void update(IBattleship battleship, Boolean hit, int x, int y) {
        updateCell(x, y);
        int notHitShipCount = battleship.countShips(null, false);
        if (notHitShipCount == 0) {
            winner = player;
            updateState("GAME OVER! Player " + (winner + 1) + " won.");
        } else {
            String status;
            if (hit == null) {
                status = "Player " + (player + 1) + " missed!";
            } else {
                status = "Player " + (player + 1) + (hit ? " sunk your ship!" : " hit!");
            }
            player = (player + 1) % 2;
            updateState(status + " Player " + (player + 1) + randomResponse());
        }
    }
}
