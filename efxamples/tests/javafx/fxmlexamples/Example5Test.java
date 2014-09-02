package javafx.fxmlexamples;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

public class Example5Test extends GuiTest {

	@Override
	protected Parent getRootNode() {
		try {
			return FXMLLoader.load(this.getClass().getResource("Example5Test.fxml"));
		} catch (IOException e) {
			System.err.println(e);
		}
		return null;
	}

	@Test
	public void testHandleProcess() {
		TextField textField = find("#textField");
		String text = "Example5 test";
		clickOn(textField).type(text);
		clickOn("#upcaseButton");
		assertEquals(text.toUpperCase(), textField.getText());
	}
}
