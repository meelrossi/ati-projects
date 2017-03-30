package noise;

import model.ColorImage;

public abstract class NoiseGenerator {

	public ColorImage addNoiseToColorImage(ColorImage colorImage) {
		double[][] imageWithNoiseRed = new double[colorImage.getWidth()][colorImage.getHeight()];
		double[][] imageWithNoiseGreen = new double[colorImage.getWidth()][colorImage.getHeight()];
		double[][] imageWithNoiseBlue = new double[colorImage.getWidth()][colorImage.getHeight()];

		int width = colorImage.getWidth();
		int height = colorImage.getHeight();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				imageWithNoiseRed[i][j] = addNoiseToPixel(colorImage.getRed(i, j));
				imageWithNoiseGreen[i][j] = addNoiseToPixel(colorImage.getGreen(i, j));
				imageWithNoiseBlue[i][j] = addNoiseToPixel(colorImage.getBlue(i, j));
			}
		}
		return new ColorImage(imageWithNoiseRed, imageWithNoiseGreen, imageWithNoiseBlue, width, height);
	}

	public ColorImage addNoiseToImage(ColorImage rawImage) {
		double[][] imageWithNoise = new double[rawImage.getWidth()][rawImage.getHeight()];
		int width = rawImage.getWidth();
		int height = rawImage.getHeight();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				imageWithNoise[i][j] = addNoiseToPixel(rawImage.getGrey(i, j));
			}
		}
		return new ColorImage(imageWithNoise, width, height);
	}

	public abstract double addNoiseToPixel(double pixel);

}
