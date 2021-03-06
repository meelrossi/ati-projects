package filter;

import model.ColorImage;

public class GaussianFilter extends Filter{
	
	public double stand;
	
	public GaussianFilter(double stand) {
		this.stand = stand;
	}
	
	@Override
	public ColorImage filter(ColorImage img, int windowSize) {
		int width = img.getWidth();
		int height = img.getHeight();
		double[][] red = new double[width][height];
		double[][] green = new double[width][height];
		double[][] blue = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				red[i][j] = gaussian(img.getRedChannel(), i, j, width, height, windowSize);
				green[i][j] = gaussian(img.getGreenChannel(), i, j, width, height, windowSize);
				blue[i][j] = gaussian(img.getBlueChannel(), i, j, width, height, windowSize);
			}
		}
		ColorImage result = new ColorImage(red, green, blue, width, height);
		result.normalize();
		return result;
	}
	
	public double gaussian(double[][] channel, int x, int y, int width, int height, int windowSize) {
		double sum = 0;
		int move = (windowSize - 1) / 2;
		for (int i = x - move; i <= x + move; i++) {
			for (int j = y - move; j <= y + move; j++) {
				if (i >= 0 && i < width && j >= 0 && j < height && !(x == i && y == j)) {
					int dx = Math.abs(x - i);
					int dy = Math.abs(y - j);
					sum += gaussianValue(dx, dy) * channel[i][j];
				}
			}
		}
		return sum;
	}
	
	public double gaussianValue(double x, double y) {
		return 1 / (2 * Math.PI * stand * stand) * Math.exp(-(x*x + y*y) / (stand * stand));
	}

}
