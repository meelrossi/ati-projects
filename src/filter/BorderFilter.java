package filter;

import model.ColorImage;

public class BorderFilter extends Filter {

	@Override
	public ColorImage filter(ColorImage img, int windowSize) {
		int width = img.getWidth();
		int height = img.getHeight();
		double[][] red = new double[width][height];
		double[][] green = new double[width][height];
		double[][] blue = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				red[i][j] = borderFilter(img.getRedChannel(), i, j, width, height, windowSize);
				green[i][j] = borderFilter(img.getGreenChannel(), i, j, width, height, windowSize);
				blue[i][j] = borderFilter(img.getBlueChannel(), i, j, width, height, windowSize);
			}
		}
		ColorImage result = new ColorImage(red, green, blue, width, height);
		result.normalize();
		return result;
	}
	
	public double borderFilter(double[][] channel, int x, int y, int width, int height, int windowSize) {
		double sum = 0;
		int move = (windowSize - 1) / 2;
		int count = 0;
		for (int i = x - move; i <= x + move; i++) {
			for (int j = y - move; j <= y + move; j++) {
				if (i >= 0 && i < width && j >= 0 && j < height && !(x == i && y == j)) {
					sum -= channel[i][j];
					count ++;
				}
			}
		}
		sum += count * channel[x][y];
		return sum;
		
	}

}
