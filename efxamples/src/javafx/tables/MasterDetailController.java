package javafx.tables;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

public class MasterDetailController {

	@FXML
	private TableView<Person> personTableView;
	@FXML
	private TableColumn<Person,String> personTableNameColumn;
	@FXML
	private TableColumn<Person,String> personTableEmailColumn;
	
	private ObservableList<Person> personList;
	
	@FXML
	private PersonController personPaneController;
	
	@FXML
	private void initialize() {
		personTableNameColumn.setCellValueFactory((dataFeatures) -> dataFeatures.getValue().nameProperty());
		personTableNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		personTableNameColumn.setOnEditCommit((cellEditEvent) -> {
            int row = cellEditEvent.getTablePosition().getRow();
			Person person = cellEditEvent.getTableView().getItems().get(row);
			person.setName(cellEditEvent.getNewValue());
        });

		personTableEmailColumn.setCellValueFactory((dataFeatures) -> dataFeatures.getValue().emailProperty());
		personTableEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		personTableNameColumn.setOnEditCommit((cellEditEvent) -> {
            int row = cellEditEvent.getTablePosition().getRow();
			Person person = cellEditEvent.getTableView().getItems().get(row);
			person.setEmail(cellEditEvent.getNewValue());
        });
		
		ReadOnlyObjectProperty<Person> selectionProperty = personTableView.getSelectionModel().selectedItemProperty();
		selectionProperty.addListener((observable, oldValue, newValue) -> {
				personPaneController.setModel(newValue);
			}
		);
		personList = personTableView.getItems();
	}

	@FXML
	private void addPerson() {
		personList.add(new Person());
		personTableView.getSelectionModel().select(personList.size() - 1);
	}
}
