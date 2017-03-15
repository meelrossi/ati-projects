package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import model.CustomImage;

public class BorderPaneViewController implements Initializable {

	@FXML
	private ImageView mainImage;
	@FXML
	private Region pixelColor;
	@FXML
	private ColorPicker changeColorButton;
	@FXML
	private CustomMenuBar customMenuBar;

	private CustomImage img;
	private int x;
	private int y;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeColorPicker();
		customMenuBar.initialize(this);
	}

	public void openImage(File file) {
		try {
			img = new CustomImage(ImageIO.read(file));
			mainImage.setImage(SwingFXUtils.toFXImage(img.getBufferedImage(), null));
			mainImage.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					x = (int) event.getX();
					y = (int) event.getY();
					refreshPixelColor();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveImage(File file) {
		img.saveOn(file);
	}

	private void initializeColorPicker() {
		changeColorButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				refreshPixelColor();
				Image image = SwingFXUtils.toFXImage(img.getBufferedImage(), null);
				mainImage.setImage(image);
			}
		});
	}

	private void refreshPixelColor() {
		pixelColor.setStyle("-fx-background-color:" + img.getHexColor(x, y) + ";");
		changeColorButton.setValue(img.getColor(x, y));
	}

}
