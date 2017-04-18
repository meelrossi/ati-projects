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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.ColorImage;
import model.ColorImageType;
import utils.ImageManager;

public class ContrastPane extends Pane {

	@FXML
	private TextField r1;
	@FXML
	private TextField r2;
	@FXML
	private TextField s1;
	@FXML
	private TextField s2;
	@FXML
	private OpenImage image;
	@FXML
	private SaveImage result;
	@FXML
	private Button calculateButton;

	public ContrastPane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/contrastPane.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void initialize() {
		image.initialize(this::calculateContrastImage);
		calculateButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				calculateContrastImage();
			}
		});
	}
	
	public void calculateContrastImage() {
		if (r1.getText() != null && r2.getText() != null && s1.getText() != null && s2.getText() != null) {
			try {
				Double value_r1 = Double.parseDouble(r1.getText());
				Double value_r2 = Double.parseDouble(r2.getText());
				Double value_s1 = Double.parseDouble(s1.getText());
				Double value_s2 = Double.parseDouble(s2.getText());
				result.setImage(ImageManager.calculateContrast(image.getImage(), value_r1, value_r2, value_s1, value_s2));
			} catch (Exception e) {
	
			}		
		}
	}
}
