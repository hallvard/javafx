package javafx.subcontrollerexample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PersonController {

	@FXML
	private TextField nameTextField;
	
	@FXML
	private TextField emailTextField;
	
	@FXML
	private void initialize() {
		nameTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (! newValue) {
				updateNameModel();
			}
		});
		emailTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (! newValue) {
				updateEmailModel();
			}
		});
	}

	private Person model;

	private ChangeListener<String> nameChangeListener = (ObservableValue<? extends String> property, String oldValue, String newValue) -> {
		updateNameView();
	};
	private ChangeListener<String> emailChangeListener = (ObservableValue<? extends String> property, String oldValue, String newValue) -> {
		updateEmailView();
	};

	public Person getModel() {
		return model;
	}

	public void setModel(Person model) {
		if (this.model != null) {
			model.nameProperty().removeListener(nameChangeListener);
			model.emailProperty().removeListener(emailChangeListener);
		}
		this.model = model;
		updateView();
		if (this.model != null) {
			model.nameProperty().addListener(nameChangeListener);
			model.emailProperty().addListener(emailChangeListener);
		}
	}

	private void updateView() {
		updateNameView();
		updateEmailView();
	}

	private void updateEmailView() {
		String email = (model != null ? model.getEmail() : null);
		emailTextField.setText(email != null ? email : "");
	}

	private void updateNameView() {
		String name = (model != null ? model.getName() : null);
		nameTextField.setText(name != null ? name : "");
	}

	private void updateModel() {
		updateNameModel();
		updateEmailModel();
	}

	private void updateEmailModel() {
		if (model != null) {
			model.setEmail(emailTextField.getText().trim());
		}
	}

	private void updateNameModel() {
		if (model != null) {
			model.setName(nameTextField.getText().trim());
		}
	}
}
