package controller.tab;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import components.view.OpenImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import model.ColorImage;
import utils.SiftMatcher;

public class ImageDetectorTab extends Tab {
	@FXML
	private OpenImage image1;
	@FXML
	private OpenImage image2;
	@FXML
	private ImageView image3;

	public ImageDetectorTab() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/imageDetectorTab.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void initialize() {
		image1.initialize(this::checkResults);
		image2.initialize(this::checkResults);
	}

	public void checkResults() {
		if (image1.getImage() != null && image2.getImage() != null) {
			try {
				SiftMatcher.match(image1.getImage().getFile(), image2.getImage().getFile());
				File keyPointsFile1 = new File("image1WithKeyPoints.jpg");
				ColorImage keyPoints1 = new ColorImage(ImageIO.read(keyPointsFile1), keyPointsFile1);
				File keyPointsFile2 = new File("image2WithKeyPoints.jpg");
				ColorImage keyPoints2 = new ColorImage(ImageIO.read(keyPointsFile2), keyPointsFile2);
				File matchesFile = new File("matchoutput.jpg");
				ColorImage matches = new ColorImage(ImageIO.read(matchesFile), matchesFile);
				image1.setImage(keyPoints1);
				image2.setImage(keyPoints2);
				image3.setImage(SwingFXUtils.toFXImage(matches.getBufferedImage(), null));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
