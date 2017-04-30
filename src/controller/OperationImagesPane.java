package controller;

import java.io.IOException;

import components.view.OpenImage;
import components.view.SaveImage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import model.ColorImage;
import utils.ImageManager;
import utils.ImageOperation;

public class OperationImagesPane extends Pane {
	@FXML
	private OpenImage image1;
	@FXML
	private OpenImage image2;
	@FXML
	private SaveImage result;
	
	private ImageOperation op;

	public OperationImagesPane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/operationImagesPane.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void initialize() {
		image1.initialize(this::checkResult);
		image2.initialize(this::checkResult);
	}

	public void setOperation(ImageOperation op) {
		this.op = op;
		checkResult();
	}

	public void checkResult() {
		ColorImage img1 = image1.getImage();
		ColorImage img2 = image2.getImage();
		if (img1 != null && img2 != null) {
			result.setImage(ImageManager.calculate(img1, img2, op));
		}
	}

}
