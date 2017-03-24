package controller;

import javafx.embed.swing.SwingFXUtils;
import model.ColorImage;
import utils.ImageManager;

public class LimitImageWithColorPane extends LimitImagePane {
	public void checkResult() {
		if (img != null) {
			result = ImageManager.limitImageWithColor((ColorImage) img, limitSlider.getValue());
			resultImage.setImage(SwingFXUtils.toFXImage(result.getBufferedImage(), null));
		}
	}
}
