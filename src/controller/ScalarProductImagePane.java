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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.ColorImage;
import model.CustomImage;
import utils.ImageManager;

public class ScalarProductImagePane extends Pane {
	
	@FXML
	private ImageView image;
	@FXML
	private ImageView resultImage;
	@FXML
	private Button loadImageButton;
	@FXML
	private Slider scalarSlider;
	@FXML
	private Label scalarLabel;
	
	private FileChooser fileChooser = new FileChooser();
	
	private CustomImage img;
	
	private CustomImage result;

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
		scalarSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				scalarLabel.setText(String.format("%.2f", scalarSlider.getValue()));
				checkResult();
			}
			
		});
	}
	
	public void checkResult() {
		if (img != null) {
			result = ImageManager.scalarProduct((ColorImage) img, scalarSlider.getValue());
			resultImage.setImage(SwingFXUtils.toFXImage(result.getBufferedImage(), null));
		}
	}

}
