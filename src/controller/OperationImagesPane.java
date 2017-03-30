package controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

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
import javafx.util.Pair;
import model.ColorImage;
import model.ColorImageType;
import utils.ImageManager;
import utils.ImageOperation;

public class OperationImagesPane extends Pane {
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
	private ImageOperation op;

	private FileChooser fileChooser = new FileChooser();

	public OperationImagesPane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/operationImagesPane.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setOperation(ImageOperation op) {
		this.op = op;
		img1 = null;
		img2 = null;
		result = null;
		image1.setImage(null);
		image2.setImage(null);
		resultImage.setImage(null);
	}

	public void initialize() {
		loadImage1Button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					try {
						if (file.getName().toLowerCase().contains(".raw")) {
							OpenRawImageDialog dialog = new OpenRawImageDialog();
							Optional<Pair<Integer, Integer>> result = dialog.showAndWait();
							result.ifPresent(d -> {
								img1 = ImageManager.readFromRaw(file, result.get().getKey(), result.get().getValue());
							});
						} else {
							OpenColorImageDialog dialog = new OpenColorImageDialog();
							Optional<ColorImageType> result = dialog.showAndWait();
							if (result.isPresent()) {
								img1 = new ColorImage(ImageIO.read(file));
								if (result.get() == ColorImageType.BLACK_AND_WHITE) {
									img1.toBlackAndWhite();
								}
							}
						}
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
						if (file.getName().toLowerCase().contains(".raw")) {
							OpenRawImageDialog dialog = new OpenRawImageDialog();
							Optional<Pair<Integer, Integer>> result = dialog.showAndWait();
							result.ifPresent(d -> {
								img2 = ImageManager.readFromRaw(file, result.get().getKey(), result.get().getValue());
							});
						} else {
							OpenColorImageDialog dialog = new OpenColorImageDialog();
							Optional<ColorImageType> result = dialog.showAndWait();
							if (result.isPresent()) {
								img2 = new ColorImage(ImageIO.read(file));
								if (result.get() == ColorImageType.BLACK_AND_WHITE) {
									img2.toBlackAndWhite();
								}
							}
						}
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
			result = ImageManager.calculate(img1, img2, op);
			resultImage.setImage(SwingFXUtils.toFXImage(result.getBufferedImage(), null));
		}
	}

}
