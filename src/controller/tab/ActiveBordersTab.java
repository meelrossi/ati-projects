package controller.tab;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import active_borders.ActiveBordersType;
import active_borders.PixelExchangeBorder;
import components.EnumButton;
import components.view.SaveImage;
import controller.JavaFXApplication;
import controller.OpenColorImageDialog;
import controller.OpenRawImageDialog;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.ColorImage;
import model.ColorImageType;
import utils.ClipBoard;
import utils.ImageManager;

public class ActiveBordersTab extends Tab {

	@FXML
	private Button oneButton;
	@FXML
	private Button sequenceButton;
	@FXML
	private Rectangle selectionRectangle;
	@FXML
	private ImageView image;
	@FXML
	private SaveImage resultImage;
	@FXML
	private Button open;
	@FXML
	private Button openFromClipboard;

	private List<EnumButton> buttons;
	private EnumButton selectedButton;

	PixelExchangeBorder peb;
	private Point dragPos;
	private FileChooser fileChooser = new FileChooser();
	private ColorImage img;
	private List<ColorImage> images;

	public ActiveBordersTab() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/activeBordersTab.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void initialize() {
		buttons = new LinkedList<EnumButton>();
		images = new LinkedList<ColorImage>();
		buttons.add(new EnumButton(oneButton, ActiveBordersType.ONE));
		buttons.add(new EnumButton(sequenceButton, ActiveBordersType.SEQUENCE));
		buttons.forEach((bd -> {
			bd.getButton().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setBackground(bd.getButton());
					selectedButton = bd;
				}
			});
		}));
		selectedButton = buttons.get(0);
		open.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (selectedButton.getType() == ActiveBordersType.ONE) {
					File file = fileChooser.showOpenDialog(JavaFXApplication.primaryStage);
					if (file != null) {
						try {
							if (file.getName().toLowerCase().contains(".raw")) {
								OpenRawImageDialog dialog = new OpenRawImageDialog();
								Optional<Pair<Integer, Integer>> result = dialog.showAndWait();
								result.ifPresent(d -> {
									img = ImageManager.readFromRaw(file, result.get().getKey(),
											result.get().getValue());
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
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					images.add(img);
				} else {
					List<File> files = fileChooser.showOpenMultipleDialog(JavaFXApplication.primaryStage);
					if (files.size() != 0) {
						try {
							for (int i = 0; i < files.size(); i++) {
								images.add(new ColorImage(ImageIO.read(files.get(i))));
							}
							image.setImage(SwingFXUtils.toFXImage(images.get(0).getBufferedImage(), null));
						} catch (IOException e) {
							e.printStackTrace();
						}
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
				}
			}
		});
		image.onMousePressedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dragPos = new Point((int) event.getX(), (int) event.getY());
				resetRectangle();
			}
		});
		image.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				int width = Math.abs(dragPos.x - (int) event.getX());
				int height = Math.abs(dragPos.y - (int) event.getY());
				selectionRectangle.setWidth(width);
				selectionRectangle.setHeight(height);
			}
		});

		image.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				int width = (int) selectionRectangle.getWidth();
				int height = (int) selectionRectangle.getHeight();
				peb = new PixelExchangeBorder(images.get(0), dragPos.x, dragPos.y, width, height);
				peb.rearrangeBorders();
				resultImage.setImage(peb.getImageWithBorder());
				for (int i = 1; i < images.size(); i++) {
					image.setImage(SwingFXUtils.toFXImage(img.getBufferedImage(), null));
					peb.setNewImage(images.get(i));
					peb.rearrangeBorders();
					try {
						resultImage.setImage(peb.getImageWithBorder());
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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

	public void setBackground(Button btn) {
		String notSelectedStyle = "-fx-background-color:#F0F8FF";
		String selectedStyle = "-fx-background-color:#1e90ff";
		buttons.forEach(nb -> {
			Button b = nb.getButton();
			b.setStyle(b != btn ? notSelectedStyle : selectedStyle);
		});
	}

}
