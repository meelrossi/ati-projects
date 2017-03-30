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
				controller.setImage(img);
			}
		});
		loadImageButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					try {
						if (file.getName().toLowerCase().contains(".raw")) {
							OpenRawImageDialog dialog = new OpenRawImageDialog();
							Optional<Pair<Integer, Integer>> result = dialog.showAndWait();
							result.ifPresent(d -> {
								img = ImageManager.readFromRaw(file, result.get().getKey(), result.get().getValue());
							});
						} else {
							OpenColorImageDialog dialog = new OpenColorImageDialog();
							Optional<ColorImageType> result = dialog.showAndWait();
							if (result.isPresent()) {
								img = new ColorImage(ImageIO.read(file));
								if (result.get() == ColorImageType.BLACK_AND_WHITE) {
									img.toBlackAndWhite();
								}
							}
						}
						image.setImage(SwingFXUtils.toFXImage(img.getBufferedImage(), null));
						calculateHistograms();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public void setNoiseType(NoiseType imageType) {
		textField2.setVisible(imageType == NoiseType.GAUSSIAN || imageType == NoiseType.SALT_AND_PEPPER);
	}

	public double getVal1() {
		return Double.parseDouble(textField1.getText());
	}

	public double getVal2() {
		return Double.parseDouble(textField2.getText());
	}

}
