package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import model.ColorImage;

public class EqualizingTab extends Tab {
	
	@FXML
	private ImageWithHistogramPane mainImagePane;
	@FXML
	private ImageWithHistogramPane resultImagePane;

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
		resultImagePane.setImage(img);
	}
}
