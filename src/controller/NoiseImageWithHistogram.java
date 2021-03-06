package controller;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import noise.NoiseType;

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
		textField3.setPromptText("percentage");
	}

	public void setNoiseType(NoiseType imageType) {
		textField1.setPromptText(imageType.getPlaceholder1());
		textField2.setPromptText(imageType.getPlaceholder2());
		textField2.setVisible(imageType.getPlaceholder2() != "");
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
