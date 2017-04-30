package components.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class ViewComponent extends Pane {
	
	public ViewComponent(String fxmlFileName) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
