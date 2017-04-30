package components.view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import controller.JavaFXApplication;
import controller.OpenColorImageDialog;
import controller.OpenRawImageDialog;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.ColorImage;
import model.ColorImageType;
import utils.ClipBoard;
import utils.ImageManager;

public class OpenImage extends ViewComponent {

	@FXML
	private ImageView image;
	@FXML
	private Button open;
	@FXML
	private Button openFromClipboard;
	
	Runnable onLoad;

	private ColorImage img;
	private FileChooser fileChooser = new FileChooser();

	public OpenImage() {
		super("/view/openImage.fxml");
	}

	public void initialize(Runnable onLoadFunction) {
		this.onLoad = onLoadFunction;
		open.setOnAction(new EventHandler<ActionEvent>() {
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
						onLoad.run();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		openFromClipboard.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ColorImage clipBoardImage = ClipBoard.pasteImage();
				if (clipBoardImage != null) {
					img = clipBoardImage;
					image.setImage(SwingFXUtils.toFXImage(img.getBufferedImage(), null));
					onLoad.run();
				}
			}
		});
	}
	
	public ColorImage getImage() {
		return img;
	}

}
