package filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.ColorImage;

public class WeightedMedianFilter extends Filter {

	@Override
	public ColorImage filter(ColorImage img, int windowSize) {
		int width = img.getWidth();
		int height = img.getHeight();
		double[][] red = new double[width][height];
		double[][] green = new double[width][height];
		double[][] blue = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				red[i][j] = wightedMedian(img.getRedChannel(), i, j, width, height);
				green[i][j] = wightedMedian(img.getGreenChannel(), i, j, width, height);
				blue[i][j] = wightedMedian(img.getBlueChannel(), i, j, width, height);
			}
		}
		return new ColorImage(red, green, blue, width, height);
	}
	
	public double wightedMedian(double[][] channel, int x, int y, int width, int height) {
		List<Double> list = new ArrayList<Double>();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i >= 0 && i < width && j >= 0 && j < height) {
					int repetitions = 0;
					if (i != x && j != y) {
						repetitions = 1;
					} else if (i == x && j == y){
						repetitions = 4;
					} else {
						repetitions = 2;
					}
					for (int k = 0; k < repetitions; k++) {
						list.add(channel[i][j]);
					}
				}
			}
		}
		Collections.sort(list);
		if (list.size() % 2 == 0) {
			double half = list.size() / 2.0;
			return (list.get((int)Math.floor(half)) + list.get((int)Math.ceil(half))) / 2;
		}
		return list.get(list.size() / 2);
	}

}
