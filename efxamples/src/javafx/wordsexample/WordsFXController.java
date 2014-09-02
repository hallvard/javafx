package javafx.wordsexample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class WordsFXController {

	@FXML TextField wordsText;
	@FXML Button processButton;
	@FXML Labeled wordCountText;
	@FXML ListView<String> wordsList;

	@FXML
	void handleProcessWords(ActionEvent event) {
		String text = wordsText.getText();
		String[] words = text.split("\\s");
		wordCountText.setText(String.valueOf(words.length));
		ObservableList<String> previous = wordsList.getItems();
		if (! previous.contains(text)) {
			previous.add(text);
		}
	}
	
	@FXML
	private void handleUseFromList(ActionEvent event) {
		wordsText.setText(wordsList.getSelectionModel().getSelectedItem());
		processButton.fire();
	}
}
