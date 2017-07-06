package controller.tab;

import java.io.IOException;

import components.view.OpenImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import utils.GSift;

public class GSiftTab extends Tab{
	@FXML
	private OpenImage image1;
	@FXML
	private ImageView image3;

	public GSiftTab() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/gSiftTab.fxml"));
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
	}

	public void checkResults() {
		if (image1.getImage() != null) {
			GSift gsift = GSift.getInstance();
			String imageName = gsift.findImage(image1.getImage().getFile());
			System.out.println(imageName);
			image3.setImage(SwingFXUtils.toFXImage(GSift.getImage(imageName).getBufferedImage(), null));
		}
	}
}
