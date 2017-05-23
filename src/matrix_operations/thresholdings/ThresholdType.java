package matrix_operations.thresholdings;

import model.ColorImage;
import utils.ImageManager;

public enum ThresholdType {

	GLOBAL, OTSU;

	public ColorImage getImageWithThreshold(ColorImage img) {
		int tRed = 0;
		int tGreen = 0;
		int tBlue = 0;
		switch (this) {
		case GLOBAL:
			tRed = GlobalThreshholding.getThreshold(img.getRedChannel());
			tGreen = GlobalThreshholding.getThreshold(img.getGreenChannel());
			tBlue = GlobalThreshholding.getThreshold(img.getBlueChannel());
			break;
		case OTSU:
			tRed = OtsuMethodThresholding.getThreshold(img.getRedChannel());
			tGreen = OtsuMethodThresholding.getThreshold(img.getGreenChannel());
			tBlue = OtsuMethodThresholding.getThreshold(img.getBlueChannel());
			break;
		}

		double[][] red = ImageManager.applyThreshold(img.getRedChannel(), tRed);
		double[][] green = ImageManager.applyThreshold(img.getGreenChannel(), tGreen);
		double[][] blue = ImageManager.applyThreshold(img.getBlueChannel(), tBlue);
		return new ColorImage(red, green, blue, img.getWidth(), img.getHeight());
	}
}
