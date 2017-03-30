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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.ColorImage;
import model.ColorImageType;
import utils.ImageManager;

public class ContrastPane extends Pane {

	@FXML
	private TextField r1;
	@FXML
	private TextField r2;
	@FXML
	private TextField s1;
	@FXML
	private TextField s2;
	@FXML
	private ImageView image;
	@FXML
	private ImageView resultImage;
	@FXML
	private Button loadImageButton;
	@FXML
	private Button calculateButton;
	
	private ColorImage img;
	private ColorImage result;
	private FileChooser fileChooser = new FileChooser();

	public ContrastPane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/contrastPane.fxml"));
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
						if (file.getName().toLowerCase().contains(".raw")) {
							OpenRawImageDialog dialog = new OpenRawImageDialog();
							Optional<Pair<Integer, Integer>> result = dialog.showAndWait();
							result.ifPresent(d -> {
							    img = ImageManager.readFromRaw(file, result.get().getKey(), result.get().getValue());
							});
						} else {
							OpenColorImageDialog dialog = new OpenColorImageDialog();
							Optional<ColorImageType> result = dialog.showAndWait();
							if (result.isPresent()){
								img = new ColorImage(ImageIO.read(file));
							    if (result.get() == ColorImageType.BLACK_AND_WHITE) {
							    	img.toBlackAndWhite();
							    }
							}
						}
						image.setImage(SwingFXUtils.toFXImage(img.getBufferedImage(), null));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		calculateButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println("dlasdjlaksdj");
				if (r1.getText() != null && r2.getText() != null && s1.getText() != null && s2.getText() != null) {
					calculateContrastImage();
					System.out.println("daksjdhakjshd");
				}
			}
		});
	}
	
	public void calculateContrastImage() {
		Double value_r1 = Double.parseDouble(r1.getText());
		Double value_r2 = Double.parseDouble(r2.getText());
		Double value_s1 = Double.parseDouble(s1.getText());
		Double value_s2 = Double.parseDouble(s2.getText());
		result = ImageManager.calculateContrast(img, value_r1, value_r2, value_s1, value_s2);
		resultImage.setImage(SwingFXUtils.toFXImage(result.getBufferedImage(), null));
	}
}
