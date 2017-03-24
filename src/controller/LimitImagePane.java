package controller;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.ColorImage;
import model.CustomImage;
import utils.ImageManager;

public class LimitImagePane extends Pane {
	@FXML
	protected ImageView image;
	@FXML
	protected ImageView resultImage;
	@FXML
	protected Button loadImageButton;
	@FXML
	protected Slider limitSlider;

	private FileChooser fileChooser = new FileChooser();

	protected CustomImage img;

	protected CustomImage result;

	public LimitImagePane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/limitImagePane.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void initialize() {
		loadImageButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					try {
						img = new ColorImage(ImageIO.read(file));
						image.setImage(SwingFXUtils.toFXImage(img.getBufferedImage(), null));
						checkResult();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		limitSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				checkResult();
			}

		});
	}

	public void checkResult() {
		if (img != null) {
			result = ImageManager.limitImage((ColorImage) img, limitSlider.getValue());
			resultImage.setImage(SwingFXUtils.toFXImage(result.getBufferedImage(), null));
		}
	}
}
