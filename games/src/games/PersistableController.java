package games;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;

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
	private ComboBox<String> stateUrlCombo;

	@FXML
	private void browseStateFile() {
		File file = new FileChooser().showOpenDialog(null);
		if (file != null) {
			stateUrlCombo.setValue(file.toURI().toString());
		}
	}

	@FXML
	private void saveStateToFile() {
		File file = new FileChooser().showSaveDialog(null);
		if (file != null) {
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(file);
				persistable.save(outputStream);
				outputStream.close();
			} catch (IOException e) {
				updateable.updateState(e.getMessage());
			} finally {
				try {
					outputStream.close();
				} catch (Exception e) {
				}
			}
		}
	}

	@FXML
	public void loadStateFromURL() {
		loadStateFromURL(stateUrlCombo.getValue());
	}

	public void loadStateFromURL(String stateUrl) {
		try {
			loadStateFromURL(new URL(stateUrl));
		} catch (IOException e) {
			updateable.updateState(e.getMessage());
		}
	}
	
	public void loadStateFromURL(URL stateUrl) {
		try {
			loadStateFromInputStream(stateUrl.openStream(), stateUrl.toString());
		} catch (IOException e) {
			updateable.updateState(e.getMessage());
		}
	}
	
	public void loadStateFromInputStream(InputStream inputStream, String stateUrl) {
		try {
			persistable.load(inputStream);
			if (stateUrl != null) {
				List<String> items = stateUrlCombo.getItems();
				if (items.contains(stateUrl)) {
					items.add(stateUrl);
					stateUrlCombo.getSelectionModel().select(items.size() - 1);
				}
			}
			updateable.updateState(true);
		} catch (IOException e) {
			updateable.updateState(e.getMessage());
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
			}
		}
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
	public
	void copyToClipboard() {
		ClipboardContent content = new ClipboardContent();
		content.putString(stateText.getText());
		Clipboard.getSystemClipboard().setContent(content);
	}

	@FXML
	public
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
