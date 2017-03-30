package filter;

import model.ColorImage;

public class MeanFilter extends Filter {

	@Override
	public ColorImage filter(ColorImage img, int windowSize) {
		int width = img.getWidth();
		int height = img.getHeight();
		double[][] red = new double[width][height];
		double[][] green = new double[width][height];
		double[][] blue = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				red[i][j] = mean(img.getRedChannel(), i, j, width, height, windowSize);
				green[i][j] = mean(img.getGreenChannel(), i, j, width, height, windowSize);
				blue[i][j] = mean(img.getBlueChannel(), i, j, width, height, windowSize);
			}
		}
		return new ColorImage(red, green, blue, width, height);
	}
	
	public double mean(double[][] channel, int x, int y, int width, int height, int windowSize) {
		double sum = 0;
		int count = 0;
		int move = (windowSize - 1) / 2;
		for (int i = x - move; i <= x + move; i++) {
			for (int j = y - move; j <= y + move; j++) {
				if (i >= 0 && i < width && j >= 0 && j < height) {
					count ++;
					sum += channel[i][j];
				}
			}
		}
		return sum / count;
	}

}
