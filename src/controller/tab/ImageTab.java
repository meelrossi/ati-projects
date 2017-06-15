package controller.tab;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

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

	public void initialize() {
		initializeColorPicker();
		customMenuBar.initialize(this);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = Highgui.imread("resources/dog.pgm", Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		int erosion_size = 5;
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS,
				new Size(2 * erosion_size + 1, 2 * erosion_size + 1), new org.opencv.core.Point(erosion_size, erosion_size));
		Imgproc.erode(img, img, element);
		toBufferedImage(img);
		// img1 = cv2.imread('box.png',0)
		// img2 = cv2.imread('box_in_scene.png',0) # trainImage
		//
		// # Initiate SIFT detector
		// sift = cv2.SIFT()
		//
		// # find the keypoints and descriptors with SIFT
		// kp1, des1 = sift.detectAndCompute(img1,None)
		// kp2, des2 = sift.detectAndCompute(img2,None)
		//
		// # FLANN parameters
		// FLANN_INDEX_KDTREE = 0
		// index_params = dict(algorithm = FLANN_INDEX_KDTREE, trees = 5)
		// search_params = dict(checks=50) # or pass empty dictionary
		//
		// flann = cv2.FlannBasedMatcher(index_params,search_params)
		//
		// matches = flann.knnMatch(des1,des2,k=2)
		//
		// # Need to draw only good matches, so create a mask
		// matchesMask = [[0,0] for i in xrange(len(matches))]
		//
		// # ratio test as per Lowe's paper
		// for i,(m,n) in enumerate(matches):
		// if m.distance < 0.7*n.distance:
		// matchesMask[i]=[1,0]
		//
		// draw_params = dict(matchColor = (0,255,0),
		// singlePointColor = (255,0,0),
		// matchesMask = matchesMask,
		// flags = 0)
		//
		// img3 =
		// cv2.drawMatchesKnn(img1,kp1,img2,kp2,matches,None,**draw_params)
		//
		// plt.imshow(img3,),plt.show();
	}

	public BufferedImage toBufferedImage(Mat m) {
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		return image;

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
