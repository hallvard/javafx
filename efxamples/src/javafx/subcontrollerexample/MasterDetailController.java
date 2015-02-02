package javafx.subcontrollerexample;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MasterDetailController {

	@FXML
	private ListView<Person> personListView;
	
	private ObservableList<Person> personList;
	
	@FXML
	private PersonController personPaneController;
	
	@FXML
	private void initialize() {
		personListView.setCellFactory((list) -> new PersonCell());
		ReadOnlyObjectProperty<Person> selectionProperty = personListView.getSelectionModel().selectedItemProperty();
		selectionProperty.addListener((observable, oldValue, newValue) -> {
				personPaneController.setModel(newValue);
			}
		);
		personList = personListView.getItems();
	}

	@FXML
	private void addPerson() {
		personList.add(new Person());
		personListView.getSelectionModel().select(personList.size() - 1);
	}
}
