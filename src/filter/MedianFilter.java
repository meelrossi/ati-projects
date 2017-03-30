package filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.ColorImage;

public class MedianFilter extends Filter {

	@Override
	public ColorImage filter(ColorImage img, int windowSize) {
		int width = img.getWidth();
		int height = img.getHeight();
		double[][] red = new double[width][height];
		double[][] green = new double[width][height];
		double[][] blue = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				red[i][j] = median(img.getRedChannel(), i, j, width, height, windowSize);
				green[i][j] = median(img.getGreenChannel(), i, j, width, height, windowSize);
				blue[i][j] = median(img.getBlueChannel(), i, j, width, height, windowSize);
			}
		}
		return new ColorImage(red, green, blue, width, height);
	}
	
	public double median(double[][] channel, int x, int y, int width, int height, int windowSize) {
		List<Double> list = new ArrayList<Double>();
		int move = (windowSize - 1) / 2;
		for (int i = x - move; i <= x + move; i++) {
			for (int j = y - move; j <= y + move; j++) {
				if (i >= 0 && i <= width && j >= 0 && j <= height && !(i == x && j == y)) {
					list.add(channel[i][j]);
				}
			}
		}
		Collections.sort(list);
		return list.get(list.size() / 2);
		
	}

}