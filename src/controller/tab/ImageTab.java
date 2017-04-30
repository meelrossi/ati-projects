package controller.tab;

import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.CustomMenuBar;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.ColorImage;
import utils.ImageManager;

public class ImageTab extends Tab {
	@FXML
	private ImageView mainImage;
	@FXML
	private Rectangle pixelColor;
	@FXML
	private ColorPicker changeColorButton;
	@FXML
	private CustomMenuBar customMenuBar;
	@FXML
	private Label pixelAmount;
	@FXML
	private Rectangle colorAvergage;
	@FXML
	private Rectangle selectionRectangle;

	private ColorImage img;
	private int x;
	private int y;
	private Point dragPos;

	public void initialize() {
		initializeColorPicker();
		customMenuBar.initialize(this);
	}

	public ImageTab() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/imageTab.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void openImage(File file) {
		try {
			img = new ColorImage(ImageIO.read(file));
			mainImage.setImage(SwingFXUtils.toFXImage(img.getBufferedImage(), null));
			mainImage.onMousePressedProperty().set(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (event.isShiftDown()) {
						dragPos = new Point((int) event.getX(), (int) event.getY());
					} else {
						x = (int) event.getX();
						y = (int) event.getY();
						refreshPixelColor();
					}
					resetRectangle();
				}
			});
			mainImage.setOnMouseDragged(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if (!event.isShiftDown())
						return;
					int width = Math.abs(dragPos.x - (int) event.getX());
					int height = Math.abs(dragPos.y - (int) event.getY());
					selectionRectangle.setWidth(width);
					selectionRectangle.setHeight(height);
				}
			});

			mainImage.setOnMouseReleased(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if (event.isShiftDown()) {
						int width = (int) selectionRectangle.getWidth();
						int height = (int) selectionRectangle.getHeight();
						pixelAmount.setText("" + width * height);
						colorAvergage.setFill(img.getAverageColor(dragPos.x, dragPos.y, width, height));
					} else {
						pixelAmount.setText("" + 1);
						colorAvergage.setFill(img.getColor(x, y));
					}
				}

			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveImage(File file) {
		img.saveOn(file);
	}
	
	public void openRawImage(File file, int width, int height) {
        img = ImageManager.readFromRaw(file, width, height);
		mainImage.setImage(SwingFXUtils.toFXImage(img.getBufferedImage(), null));
		mainImage.onMousePressedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isShiftDown()) {
					dragPos = new Point((int) event.getX(), (int) event.getY());
				} else {

					x = (int) event.getX();
					y = (int) event.getY();
					refreshPixelColor();
				}
				resetRectangle();
			}
		});
		mainImage.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (!event.isShiftDown())
					return;
				int width = Math.abs(dragPos.x - (int) event.getX());
				int height = Math.abs(dragPos.y - (int) event.getY());
				selectionRectangle.setWidth(width);
				selectionRectangle.setHeight(height);
			}
		});

		mainImage.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.isShiftDown()) {
					int width = (int) selectionRectangle.getWidth();
					int height = (int) selectionRectangle.getHeight();
					pixelAmount.setText("" + width * height);
					colorAvergage.setFill(img.getAverageColor(dragPos.x, dragPos.y, width, height));
				} else {
					pixelAmount.setText("" + 1);
					colorAvergage.setFill(img.getColor(x, y));
				}
			}

		});
	}

	public void resetRectangle() {
		selectionRectangle.setStroke(Color.BLACK);
		selectionRectangle.setFill(Color.TRANSPARENT);
		if (dragPos != null) {
			selectionRectangle.setLayoutX(dragPos.x);
			selectionRectangle.setLayoutY(dragPos.y);
		}
		selectionRectangle.setWidth(0);
		selectionRectangle.setHeight(0);
	}

	private void initializeColorPicker() {
		changeColorButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				img.setPixelColor(x, y, changeColorButton.getValue());
				refreshPixelColor();
				Image image = SwingFXUtils.toFXImage(img.getBufferedImage(), null);
				mainImage.setImage(image);
			}
		});
	}

	private void refreshPixelColor() {
		pixelColor.setFill(img.getColor(x, y));
		changeColorButton.setValue(img.getColor(x, y));
	}
}
