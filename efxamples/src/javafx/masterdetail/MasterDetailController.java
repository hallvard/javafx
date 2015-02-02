package javafx.masterdetail;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MasterDetailController {

	@FXML
	private ListView<Person> personListView;	
	@FXML
	private PersonController personPaneController;

	public void initialize() {
		personListView.setCellFactory((list) -> new PersonCell());
		Person person1 = new Person();
		person1.setName("Hallvard Trætteberg");
		person1.setEmail("hal@idi.ntnu.no");
		Person person2 = new Person();
		person2.setName("Marit Reitan");
		personListView.getItems().addAll(person1, person2);
		personListView.getSelectionModel().selectedItemProperty().addListener(
				(property, oldValue, newValue) -> { personPaneController.setModel(newValue);}
		);
	}
	
	@FXML
	private void addPerson() {
		Person person = new Person();
		personListView.getItems().add(person);
		personListView.getSelectionModel().select(personListView.getItems().size() - 1);
	}
}
