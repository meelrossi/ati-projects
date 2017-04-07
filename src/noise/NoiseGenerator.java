package noise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.ColorImage;
import utils.Pair;

public abstract class NoiseGenerator {
	
	private double percentageAffectedByNoise;
	
	public NoiseGenerator(double percentageAffectedByNoise){
		this.percentageAffectedByNoise = percentageAffectedByNoise;
	}

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
				imageWithNoise[i][j] = rawImage.getGrey(i, j);
			}
		}
		for(Pair p: pixelsToAddNoise(width, height)){
			imageWithNoise[p.getX()][p.getY()] = addNoiseToPixel(rawImage.getGrey(p.getX(),p.getY()));
		}
		return new ColorImage(imageWithNoise, width, height);
	}
	
	public List<Pair> pixelsToAddNoise(int width,int height){
		int amountOfPixels = (int)(width*height*percentageAffectedByNoise);
		List<Pair> pixels = new ArrayList<>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixels.add(new Pair(i,j));
			}
		}
		Collections.shuffle(pixels);
		if(pixels.size() > amountOfPixels){
			return pixels.subList(0, amountOfPixels);
		}
		return pixels;
	}

	public abstract double addNoiseToPixel(double pixel);

}
