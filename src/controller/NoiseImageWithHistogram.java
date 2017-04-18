package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import model.ColorImage;
import model.ColorImageType;
import noise.NoiseType;
import utils.ImageManager;

public class NoiseImageWithHistogram extends ImageWithHistogramPane {
	@FXML
	private TextField textField1;
	@FXML
	private TextField textField2;
	@FXML
	private TextField textField3;
	@FXML
	private Button calculateButton;

	@Override
	public URL getResource() {
		return getClass().getResource("/view/noiseImageWithHistogram.fxml");
	}

	public void initialize(HistogramTab controller, boolean isResult) {
		super.initialize(controller, isResult);
		textField1.setVisible(true);
		textField2.setVisible(false);
		calculateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.setImage(image.getImage());
			}
		});
		image.initialize(this::onLoadImage);
	}

	public void setNoiseType(NoiseType imageType) {
		textField2.setVisible(imageType == NoiseType.GAUSSIAN || imageType == NoiseType.SALT_AND_PEPPER);
	}

	public double getVal1() {
		return Double.parseDouble(textField1.getText());
	}

	public Double getVal2() {
		if(textField2.getText().equals("")){
			return null;
		}
		return Double.parseDouble(textField2.getText());
	}
	
	public double getVal3() {
		return Double.parseDouble(textField3.getText());
	}

}
