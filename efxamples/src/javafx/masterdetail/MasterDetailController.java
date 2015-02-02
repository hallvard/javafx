package javafx.masterdetail;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MasterDetailController {

	@FXML
	private ListView<Person> personListView;
	
	@FXML
	private PersonController personPaneController;

	@FXML
	private void addPerson() {
	}
}
