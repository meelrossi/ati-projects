package controller;

import model.ColorImage;
import utils.ImageManager;

public class LimitImageWithColorPane extends LimitImagePane {
	public void checkResult() {
		ColorImage img = image.getImage();
		if (img != null) {
			result.setImage(ImageManager.limitImageWithColor((ColorImage) img, limitSlider.getValue()));
		}
	}
}
