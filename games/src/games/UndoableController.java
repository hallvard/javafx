package games;

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
	public
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
	public
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
