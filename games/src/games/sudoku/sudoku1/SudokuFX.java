package games.sudoku.sudoku1;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import games.imagegrid.ImageGridGame;

public class SudokuFX extends ImageGridGame<String> {

	private static final String CELL_SELECTED_INDICATOR = "selected";
	private static final String CELL_INVALID_INDICATOR = "invalid";
	private static final String CELL_ASSIGNABLE_INDICATOR = "assignable";

	@FXML
	private TextField levelTextField;

	@FXML
	private Text messageText;
	
	@FXML
	protected void initialize() {
		super.initialize();
		
		Font assignableFont = Font.font("Courier", 40);
		Font notAssignableFont = Font.font("Arial", 28);
		Color invalid = Color.RED;
		Color normal = Color.BLACK;
		Color selected = Color.WHITE;
		
		for(int i = 1; i <= ISudoku.BOARD_SIZE; i++){
			imageGrid.setImage(CELL_ASSIGNABLE_INDICATOR + CELL_SELECTED_INDICATOR + i, createTextImage(i, assignableFont, Color.BLACK, selected));
			imageGrid.setImage(CELL_SELECTED_INDICATOR + i, createTextImage(i, notAssignableFont, Color.BLACK, normal));
			
			imageGrid.setImage(CELL_ASSIGNABLE_INDICATOR + CELL_INVALID_INDICATOR + i, createTextImage(i, assignableFont, Color.BLACK, invalid));
			imageGrid.setImage(CELL_INVALID_INDICATOR + i, createTextImage(i, notAssignableFont, Color.BLACK, invalid));
			
			imageGrid.setImage(CELL_ASSIGNABLE_INDICATOR + i, createTextImage(i, assignableFont, Color.BLACK, normal));
			imageGrid.setImage(Integer.toString(i), createTextImage(i, notAssignableFont, Color.BLACK, normal));
		}
		
		imageGrid.setImage(CELL_ASSIGNABLE_INDICATOR + CELL_SELECTED_INDICATOR, createTextImage("|", assignableFont, Color.BLACK, Color.WHITE));
		
		imageGrid.setCellColors(1, "#F2F2F2");
		imageGrid.setStyle("-fx-background-color: #6E6E6E;");
	}	
	
	@FXML
	private ISudoku sudoku;
	private int selectedX;
	private int selectedY;
	
	@FXML
	private void startGame() {
		fillGrid(null);
		String level = levelTextField.getText();
		sudoku.init(level);
		imageGrid.setRowCount(ISudoku.BOARD_SIZE);
		imageGrid.setColumnCount(ISudoku.BOARD_SIZE);
		update();
		ensureKeyboardFocus();
	}

	private void update() {
		for(int x = 0; x < ISudoku.BOARD_SIZE; x++){
			for(int y = 0; y < ISudoku.BOARD_SIZE; y++){
				int block = sudoku.getBlock(x, y);
				String prefix = "";
				if(! (sudoku.isLegalBlock(block) && sudoku.isLegalColumn(x) && sudoku.isLegalRow(y))){
					prefix = CELL_INVALID_INDICATOR;
				}
				if(x == selectedX && y == selectedY){
					prefix = CELL_SELECTED_INDICATOR;
				}
				if(sudoku.isAssignable(x, y)){
					prefix = CELL_ASSIGNABLE_INDICATOR + prefix;
				}
				
				Integer value = sudoku.getCellValue(x, y);
				String content = value != null ? Integer.toString(value) : "";
				
				setCell(x, y, prefix + content);
			}
		}
		
		updateMessage();
	}

	private void updateMessage() {
		if(sudoku.isSolved()){
			messageText.setText("Gratulerer! Du har en gyldig løsning.");
		} else if(! sudoku.isLegalGrid()){
			messageText.setText("Tildeling av verdier er ugyldig.");
		} else {
			messageText.setText("");
		}
	}

	@Override
	protected boolean mouseClicked(int x, int y) {
		ensureKeyboardFocus();
		selectedX = x;
		selectedY = y;
		update();

		return true;
	}
	

	@Override
	protected boolean keyPressed(KeyCode code) {
		if(code.isDigitKey() && code.ordinal() > KeyCode.DIGIT0.ordinal() && code.ordinal() <= KeyCode.DIGIT9.ordinal()){
			int value = code.ordinal() - KeyCode.DIGIT0.ordinal(); 
			
			if(value > 0 && sudoku.isValidAssignment(selectedX, selectedY, value)){
				sudoku.placeDigit(selectedX, selectedY, value);
			}
		} else if(code.equals(KeyCode.BACK_SPACE) || code.equals(KeyCode.DELETE)){
			if(sudoku.isValidAssignment(selectedX, selectedY, null)){
				sudoku.placeDigit(selectedX, selectedY, null);
			}
		}
			
		update();
		
		return true;
	}
	
	
	public static void main(String[] args){
		launch(SudokuFX.class);
	}
	
	
}
