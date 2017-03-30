package noise;

import model.RawImage;

public abstract class NoiseGenerator {

	public RawImage addNoiseToImage(RawImage rawImage){
		int[][] imageWithNoise = new int[rawImage.getWidth()][rawImage.getHeight()];
		int width = rawImage.getWidth();
		int height = rawImage.getHeight();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				imageWithNoise[i][j] = addNoiseToPixel(rawImage.getGrey(i, j));
			}
		}
		return new RawImage(imageWithNoise, width, height);
	}
	
	public abstract int addNoiseToPixel(int pixel);
	
}
