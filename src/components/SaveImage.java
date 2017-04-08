package components;

import java.io.File;

import controller.JavaFXApplication;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.ColorImage;
import utils.ClipBoard;

public class SaveImage extends ViewComponent {

	@FXML
	private ImageView image;
	@FXML
	private Button save;
	@FXML
	private Button saveToClipboard;

	private ColorImage result;
	private FileChooser fileChooser = new FileChooser();

	public SaveImage() {
		super("/view/saveImage.fxml");
	}

	public void initialize() {

		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showSaveDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					result.saveOn(file);
				}
			}
		});

		saveToClipboard.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (result != null) {
					ClipBoard.copyImage(result);
				}
			}
		});
	}
	
	public void setImage(ColorImage img) {
		result = img;
		image.setImage(SwingFXUtils.toFXImage(result.getBufferedImage(), null));
	}
}
