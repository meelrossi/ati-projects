package controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import components.OpenImage;
import components.SaveImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.ColorImage;
import model.ColorImageType;
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
