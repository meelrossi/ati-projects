package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import model.ColorImage;
import model.ColorImageType;
import model.NoiseButton;
import noise.NoiseGenerator;
import noise.NoiseType;

public class NoiseTab extends HistogramTab {
	@FXML
	private Button gaussianButton;
	@FXML
	private Button rayleighButton;
	@FXML
	private Button exponentialButton;
	@FXML
	private Button saltAndPepperButton;
	@FXML
	private NoiseImageWithHistogram mainImagePane;
	@FXML
	private ImageWithHistogramPane resultImagePane;

	@FXML
	private List<NoiseButton> buttons;

	private NoiseButton selectedButton;

	public NoiseTab() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/noiseTab.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void initialize() {
		buttons = new LinkedList<NoiseButton>();
		buttons.add(new NoiseButton(gaussianButton, NoiseType.GAUSSIAN));
		buttons.add(new NoiseButton(rayleighButton, NoiseType.RAYLEIGH));
		buttons.add(new NoiseButton(exponentialButton, NoiseType.EXPONENTIAL));
		buttons.add(new NoiseButton(saltAndPepperButton, NoiseType.SALT_AND_PEPPER));
		buttons.forEach((nb -> {
			nb.getButton().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setBackground(nb.getButton());
					mainImagePane.setNoiseType(nb.getNoiseType());
					selectedButton = nb;
				}
			});
		}));
		mainImagePane.initialize(this, false);
		resultImagePane.initialize(this, true);
	}

	public void setBackground(Button btn) {
		String notSelectedStyle = "-fx-background-color:#F0F8FF";
		String selectedStyle = "-fx-background-color:#1e90ff";
		buttons.forEach(nb -> {
			Button b = nb.getButton();
			b.setStyle(b != btn ? notSelectedStyle : selectedStyle);
		});
	}
	
	public void setImage(ColorImage img) {
		NoiseGenerator ng = selectedButton.getNoiseType().getNoiseGenerator(mainImagePane.getVal1(), mainImagePane.getVal2(), mainImagePane.getVal3());
		if (img.colorImageType() == ColorImageType.COLOR) {
			ColorImage ci = ng.addNoiseToColorImage(img);
			ci.normalize();
			resultImagePane.setImage(ci);
		} else {
			ColorImage ci = ng.addNoiseToImage(img);
			ci.normalize();
			resultImagePane.setImage(ci);
		}
	}

}
