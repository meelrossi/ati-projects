package controller;

import java.io.IOException;

import components.OpenImage;
import components.SaveImage;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.ColorImage;
import utils.ImageManager;

public class ScalarProductImagePane extends Pane {
	
	@FXML
	private OpenImage image;
	
	@FXML
	private SaveImage result;
	
	@FXML
	private Slider scalarSlider;
	
	@FXML
	private Label scalarLabel;

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
	
	public void initialize() {
		scalarSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				scalarLabel.setText(String.format("%.2f", scalarSlider.getValue()));
				checkResult();
			}
			
		});
		image.initialize(this::checkResult);
	}
	
	public void checkResult() {
		ColorImage img = image.getImage();
		if (img != null) {
			result.setImage(ImageManager.scalarProduct((ColorImage) img, scalarSlider.getValue()));
		}
	}

}
