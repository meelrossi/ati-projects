package controller;

import java.io.IOException;

import components.view.OpenImage;
import components.view.SaveImage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import model.ColorImage;

public class NegativeImagePane extends Pane {
	@FXML
	private OpenImage image;
	
	@FXML
	private SaveImage result;

	public NegativeImagePane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/negativeImagePane.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void initialize() {
		image.initialize(this::checkResult);
	}
	
	public void checkResult() {
		ColorImage img = image.getImage();
		if (img != null) {
			result.setImage(img.getNegative());
		}
	}
}
