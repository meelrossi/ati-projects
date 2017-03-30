package noise;

import random_number_generator.GaussianRandomNumberGenerator;

public class GaussianNoiseGenerator extends NoiseGenerator{
	
	private final GaussianRandomNumberGenerator gaussianRng;
	
	public GaussianNoiseGenerator(double mean, double standardDeviation) {
		this.gaussianRng = new GaussianRandomNumberGenerator(mean,standardDeviation);
	}
	
	@Override
	public double addNoiseToPixel(double pixel) {
		pixel += gaussianRng.random();
		if(pixel<0){
			return 0;
		} else if(pixel>255){
			return 255;
		}
		return pixel;
	}

}
