package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class ScalarProductImagePane extends Pane {
	
	public ScalarProductImagePane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/scalarProductImagePane.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
