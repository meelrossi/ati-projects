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

public class NegativeImagePane extends Pane {
	@FXML
	private ImageView image;
	@FXML
	private ImageView resultImage;
	@FXML
	private Button loadImageButton;
	
	private ColorImage img;
	private ColorImage result;
	private FileChooser fileChooser = new FileChooser();

	public NegativeImagePane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/negativeImagePane.fxml"));
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
	}
	
	public void checkResult() {
		if (img != null) {
			result = img.getNegative();
			resultImage.setImage(SwingFXUtils.toFXImage(result.getBufferedImage(), null));
		}
	}
}
