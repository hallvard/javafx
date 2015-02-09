package javafx.tables;

import java.time.LocalDate;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class PersonController {

	@FXML private TextField nameField;
	@FXML private Text nameFieldValid;
	
	@FXML private TextField emailField;
	@FXML private Text emailFieldValid;

	@FXML private DatePicker birthdayField;

	public void nameFieldChange(ReadOnlyStringProperty property, String oldValue, String newValue) {
		if (isNameValid(newValue)) {
			nameFieldValid.setVisible(false);
		} else {
			nameFieldValid.setVisible(true);
			nameFieldValid.setText("Wrong format");
		}
	}
	private final String NAME_REGEX = "[\\p{L} ]*";
	private boolean isNameValid(String newValue) {
		return newValue.matches(NAME_REGEX);
	}
	@FXML
	private void nameFieldFocusChange(ReadOnlyBooleanProperty property, Boolean oldValue, Boolean newValue) {
		if (! newValue) {
			updateNameModel();
		}
	}

	@FXML
	private void emailFieldChange(ReadOnlyStringProperty property, String oldValue, String newValue) {
		if (isEmailValid(newValue)) {
			emailFieldValid.setVisible(false);
		} else {
			emailFieldValid.setVisible(true);
			emailFieldValid.setText("Wrong format");
		}
	}
	private final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private boolean isEmailValid(String newValue) {
		return newValue.matches(EMAIL_REGEX);
	}
	@FXML
	private void emailFieldFocusChange(ReadOnlyBooleanProperty property, Boolean oldValue, Boolean newValue) {
		if (! newValue) {
			updateEmailModel();
		}
	}

	@FXML
	private void birthdayFieldFocusChange(ReadOnlyBooleanProperty property, Boolean oldValue, Boolean newValue) {
		if (! newValue) {
			updateBirthdayModel();
		}
	}
	
	private Person model;

	private ChangeListener<String> nameChangeListener = (property, oldValue, newValue) -> {
		updateNameView();
	};

	private ChangeListener<String> emailChangeListener = (property, oldValue, newValue) -> {
		updateEmailView();
	};
	
	private ChangeListener<LocalDate> birthdayChangeListener = (property, oldValue, newValue) -> {
		updateBirthdayView();
	};

	public Person getModel() {
		return model;
	}

	public void setModel(Person model) {
		if (this.model != null) {
			model.nameProperty().removeListener(nameChangeListener);
			model.emailProperty().removeListener(emailChangeListener);
			model.birthdayProperty().removeListener(birthdayChangeListener);
		}
		this.model = model;
		updateView();
		if (this.model != null) {
			model.nameProperty().addListener(nameChangeListener);
			model.emailProperty().addListener(emailChangeListener);
			model.birthdayProperty().addListener(birthdayChangeListener);
		}
	}

	private void updateView() {
		updateNameView();
		updateEmailView();
		updateBirthdayView();
	}

	private void updateNameView() {
		String name = (model != null ? model.getName() : null);
		nameField.setText(name != null ? name : "");
		nameField.setEditable(model != null);
	}
	
	private void updateEmailView() {
		String email = (model != null ? model.getEmail() : null);
		emailField.setText(email != null ? email : "");
		emailField.setEditable(model != null);
	}
	
	private void updateBirthdayView() {
		LocalDate birthday = (model != null ? model.getBirthday() : null);
		birthdayField.setValue(birthday != null ? birthday : null);
		birthdayField.setEditable(model != null);
	}

	private void updateNameModel() {
		if (model != null) {
			model.setName(nameField.getText().trim());
		}
	}

	private void updateEmailModel() {
		if (model != null) {
			model.setEmail(emailField.getText().trim());
		}
	}
	
	private void updateBirthdayModel() {
		if (model != null) {
			model.setBirthday(birthdayField.getValue());
		}
	}
}
