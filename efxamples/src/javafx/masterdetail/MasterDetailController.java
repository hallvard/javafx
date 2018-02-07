package javafx.masterdetail;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MasterDetailController {

	@FXML
	private ListView<Person> personListView;	
	@FXML
	private PersonController personPaneController;
	private List<Person> items;
	
	@FXML
	public void initialize() {
		personListView.setCellFactory((listView) -> new PersonCell()
//			TextFieldListCell.forListView(new StringConverter<Person>() {
//				@Override
//				public String toString(Person person) {
//					return person.getName() + " -> " + person.getEmail();
//				}
//				@Override
//				public Person fromString(String s) {
//					int pos = s.indexOf(" -> ");
//					return new Person(s.substring(0, pos), s.substring(pos + 4));
//				}
//			})
		);
		personListView.setEditable(true);
		personListView.setOnEditCommit((editEvent) -> {
			Person person = personListView.getItems().get(editEvent.getIndex());
			Person newPerson = editEvent.getNewValue();
			person.setName(newPerson.getName());
			person.setEmail(newPerson.getEmail());
		});
		
		Person person1 = new Person("Hallvard Trætteberg", "hal@idi.ntnu.no");
		Person person2 = new Person("Marit Reitan", null);
		items = personListView.getItems();
		items.add(person1);
		items.add(person2);
		personListView.getSelectionModel().selectedItemProperty().addListener(
				(property, oldValue, newValue) -> {
					personPaneController.setModel(newValue);
				}
		);
	}
	
	@FXML
	private void addPerson() {
		Person person = new Person();
		items.add(person);
		personListView.getSelectionModel().select(items.size() - 1);
	}
}
