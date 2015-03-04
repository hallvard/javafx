package games.sokoban.sokoban2;

import games.IPersistable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class PersistableController {

	@FXML
	private IPersistable persistable;
	
	public IPersistable getStateStore() {
		return persistable;
	}
	
	private IUpdateable updateable = null;
	
	public void setUpdate(IUpdateable update) {
		this.updateable = update;
	}
	
	public void setStateStore(IPersistable stateStore) {
		this.persistable = stateStore;
	}

	@FXML
	private TextArea stateText;

	@FXML
	private void loadStateFromText() {
		InputStream inputStream = new ByteArrayInputStream(stateText.getText().getBytes());
		try {
			persistable.load(inputStream);
		} catch (IOException e) {
			if (updateable != null) {
				updateable.updateState(e.getMessage());
			}
		}
		if (updateable != null) {
			updateable.updateState(true);
		}
	}
	
	@FXML
	private void saveStateToText() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			persistable.save(outputStream);
			stateText.setText(outputStream.toString());
		} catch (IOException e) {
			if (updateable != null) {
				updateable.updateState(e.getMessage());
			}
		}
	}

	@FXML
	void copyToClipboard() {
		ClipboardContent content = new ClipboardContent();
		content.putString(stateText.getText());
		Clipboard.getSystemClipboard().setContent(content);
	}

	@FXML
	void pasteFromClipboard() {
		String levelString = Clipboard.getSystemClipboard().getString();
		if (levelString != null) {
			stateText.setText(levelString);
		}
	}

	@FXML
	private ContextMenu clipboardMenu;

	public ContextMenu getClipboardMenu() {
		return clipboardMenu;
	}
}
