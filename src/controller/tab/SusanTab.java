package controller.tab;

import java.io.IOException;

import border.SusanBorderAndCorner;
import components.view.OpenImage;
import components.view.SaveImage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import model.ColorImage;

public class SusanTab extends Tab {
	
	@FXML
	private OpenImage image;
	@FXML
	private SaveImage resultImage1;
	@FXML
	private SaveImage resultImage2;
	@FXML
	private TextField tField;
	@FXML
	private TextField toleranceField;
	@FXML
	private Button calculateButton;
	
	public SusanTab() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/susanTab.fxml"));
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
		tField.setPromptText("t");
		toleranceField.setPromptText("tolerance");
		calculateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				checkResult();
			}
		});
	}
	
	public void checkResult() {
		SusanBorderAndCorner border = new SusanBorderAndCorner(Integer.parseInt(tField.getText()), Double.parseDouble(toleranceField.getText()));
		ColorImage img1 = image.getImage();
		if (img1 != null) {
			resultImage1.setImage(border.markBorders(img1));
			resultImage2.setImage(border.markCorners(img1));
		}
	}
}
