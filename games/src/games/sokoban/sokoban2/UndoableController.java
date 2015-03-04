package games.sokoban.sokoban2;

import games.IUndoable;
import javafx.fxml.FXML;

public class UndoableController {

	private IUpdateable updateable = null;
	
	public void setUpdate(IUpdateable update) {
		this.updateable = update;
	}
	
	@FXML
	private IUndoable undoable;
	
	public IUndoable getUndoRedo() {
		return undoable;
	}

	public void setUndoRedo(IUndoable undoRedo) {
		this.undoable = undoRedo;
	}
	
	@FXML
	void undo() {
		if (undoable.canUndo()) {
			undoable.undo();
			if (updateable != null) {
				updateable.updateState(false);
			}
		} else {
			if (updateable != null) {
				updateable.updateState("Could not undo");
			}
		}
	}

	@FXML
	void redo() {
		if (undoable.canRedo()) {
			undoable.redo();
			if (updateable != null) {
				updateable.updateState(false);
			}
		} else {
			if (updateable != null) {
				updateable.updateState("Could not redo");
			}
		}
	}
}
