package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.sun.javafx.iio.ImageStorage.ImageType;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class BorderPaneViewController implements Initializable {

	@FXML
	private MenuItem saveFile;
	@FXML
	private MenuItem openFile;
	@FXML
	private ImageView mainImage;
	@FXML
	private Region pixelColor;
	@FXML
	private ColorPicker changeColorButton;

	private FileChooser fileChooser;
	private BufferedImage bufferedImage;
	private int x;
	private int y;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fileChooser = new FileChooser();
		initializeOpenFile();
		initializeColorPicker();
		initializeSaveFile();
	}

	private void initializeOpenFile() {
		openFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showOpenDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					try {
						bufferedImage = ImageIO.read(file);
						Image image = SwingFXUtils.toFXImage(bufferedImage, null);
						mainImage.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								x = (int) event.getX();
								y = (int) event.getY();
								refreshPixelColor();
							}
						});
						;

						mainImage.setImage(image);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}
		});
	}

	private void initializeSaveFile() {
		saveFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showSaveDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					try {
						ImageIO.write(bufferedImage, "png", file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		});
	}

	private void initializeColorPicker() {
		changeColorButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Color color = changeColorButton.getValue();
				int red = (int) (color.getRed() * 255);
				int green = (int) (color.getGreen() * 255);
				int blue = (int) (color.getBlue() * 255);
				java.awt.Color c = new java.awt.Color(red, green, blue);
				bufferedImage.setRGB(x, y, c.getRGB());
				refreshPixelColor();
				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				mainImage.setImage(image);

			}

		});
	}

	private void refreshPixelColor() {
		Integer pepe = bufferedImage.getRGB(x, y);
		int red = (pepe >> 16) & 0xFF;
		int green = (pepe >> 8) & 0xFF;
		int blue = (pepe >> 0) & 0xFF;
		pixelColor.setStyle("-fx-background-color:" + String.format("#%02x%02x%02x", red, green, blue) + ";");
		changeColorButton.setValue(Color.rgb(red, green, blue));
	}

}
