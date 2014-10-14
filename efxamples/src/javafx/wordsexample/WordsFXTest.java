package javafx.wordsexample;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

public class WordsFXTest extends GuiTest {

	private WordsFXController controller;

	@Override
	protected Parent getRootNode() {
		FXMLLoader loader = new FXMLLoader();
		try {
			Parent root = (Parent) loader.load(this.getClass().getResourceAsStream("WordsFX.fxml"));
			controller = loader.getController();
			return root;
		} catch (IOException e) {
			System.err.println(e);
		}
		return null;
	}
	
	@Test
	public void testHandleProcess() {
		clickOn(controller.wordsText).type("Hello word test");
		clickOn(controller.processButton);
		assertEquals("3", controller.wordCountText.getText());
	}
}
