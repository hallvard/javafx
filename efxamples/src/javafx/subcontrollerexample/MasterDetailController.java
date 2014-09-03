package javafx.subcontrollerexample;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MasterDetailController {

	@FXML
	private void initialize() {
		personList.setCellFactory((ListView<Person> param) -> {
				return new PersonCell();
			}
		);
		personList.getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) -> {
				personPaneController.setModel(newValue);
			}
		);
	}

	@FXML
	private ListView<Person> personList;
	
	@FXML
	private PersonController personPaneController;
	
	@FXML
	private void addPerson() {
		ObservableList<Person> persons = personList.getItems();
		persons.add(new Person());
		personList.getSelectionModel().select(persons.size() - 1);
	}
}
