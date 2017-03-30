package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import model.ColorImage;
import utils.ImageManager;

public class EqualizingTab extends HistogramTab {

	public EqualizingTab() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/equalizingTab.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void initialize() {
		mainImagePane.initialize(this, false);
		resultImagePane.initialize(this, true);
	}

	public void setImage(ColorImage img) {
		resultImagePane.setImage(ImageManager.equalizeImage(img));
	}
}
