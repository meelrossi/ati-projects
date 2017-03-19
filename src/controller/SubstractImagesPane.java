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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.ColorImage;
import utils.ImageManager;

public class SubstractImagesPane extends Pane {
	@FXML
	private ImageView image1;
	@FXML
	private ImageView image2;
	@FXML
	private ImageView resultImage;
	@FXML
	private Button loadImage1Button;
	@FXML
	private Button loadImage2Button;

	private ColorImage img1;
	private ColorImage img2;
	private ColorImage result;
	
	private FileChooser fileChooser = new FileChooser();

	public SubstractImagesPane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/substractImagesPane.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void initialize() {
		loadImage1Button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					try {
						img1 = new ColorImage(ImageIO.read(file));
						image1.setImage(SwingFXUtils.toFXImage(img1.getBufferedImage(), null));
						checkResult();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		loadImage2Button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					try {
						img2 = new ColorImage(ImageIO.read(file));
						image2.setImage(SwingFXUtils.toFXImage(img2.getBufferedImage(), null));
						checkResult();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public void checkResult() {
		if (img1 != null && img2 != null) {
			result = ImageManager.sustraction(img1, img2);
			resultImage.setImage(SwingFXUtils.toFXImage(result.getBufferedImage(), null));
		}
	}

}
