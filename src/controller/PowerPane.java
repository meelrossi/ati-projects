package controller;

import java.io.IOException;

import components.OpenImage;
import components.SaveImage;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.ColorImage;
import utils.ImageManager;

public class PowerPane extends Pane {
	
	@FXML
	private OpenImage image;
	
	@FXML
	private SaveImage result;
	
	@FXML
	protected Slider limitSlider;

	public PowerPane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/powerPane.fxml"));
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
		limitSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				checkResult();
			}

		});
	}
	
	public void checkResult() {
		ColorImage img = image.getImage();
		if (img != null) {
			result.setImage(ImageManager.powerImage(img, limitSlider.getValue()));
		}
	}

}
