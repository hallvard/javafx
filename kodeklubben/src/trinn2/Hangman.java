package trinn2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Hangman extends Application {

	List<String> words = Arrays.asList("kode", "kurs");
	
	String word = words.get((int) (Math.random() * words.size()));

	List<String> guessed = new ArrayList<String>();
	List<String> wrong = new ArrayList<String>();
	
	int tries;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        Parent root = (Parent) fxmlLoader.load(this.getClass().getResourceAsStream("Hangman.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML Node man1, man2, man3, man4, man5, man6, man7;
    @FXML List<Node> man;
    
    @FXML
    void initialize() {
    	man = Arrays.asList(man1, man2, man3, man4, man5, man6, man7);
    	for (Node node : man) {
			node.setVisible(false);
		}
    	tries = man.size();
    	ask();
    }

    @FXML Text ask;
    @FXML TextField input;
    @FXML Text message;

	boolean ask() {
		String out = "";
		for (int i = 0; i < word.length(); i++) {
			String letter = word.substring(i, i + 1);
			if (guessed.indexOf(letter) < 0) {
				letter = "_";
			}
			out = out + letter;
		}
		ask.setText("Gjett en bokstav i ordet: " + out + ", " + tries + " forsøk igjen");
		return out.equals(word);
	}
	
	@FXML
	void checkInput() {
		String guess = input.getText();
		if (guess.length() != 1) {
			message.setText("Du må gjette én bokstav om gangen!");
		} else if (guessed.contains(guess) || wrong.contains(guess)) {
			message.setText("Bokstaven er allerede gjettet på:" + guess);
		} else if (word.indexOf(guess) >= 0) {
			message.setText("Yay");
			guessed.add(guess);
		} else {
			message.setText("Nope");
			man.get(man.size() - tries).setVisible(true);
			tries = tries - 1;
			wrong.add(guess);
		}
		input.setText("");
		input.requestFocus();
		if (ask() || tries <= 0) {
			finish();
		}
	}
	
	void finish() {
		if (tries > 0) {
			ask.setText("Du gjettet " + word);
		} else {
			ask.setText("Du klarte ikke å gjette '" + word + "'");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
