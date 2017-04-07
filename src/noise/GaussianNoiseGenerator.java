package noise;

import random_number_generator.GaussianRandomNumberGenerator;

public class GaussianNoiseGenerator extends NoiseGenerator{
	
	private final GaussianRandomNumberGenerator gaussianRng;
	
	public GaussianNoiseGenerator(double mean, double standardDeviation, double percentageAffectedByNoise) {
		super(percentageAffectedByNoise);
		this.gaussianRng = new GaussianRandomNumberGenerator(mean,standardDeviation);
	}
	
	@Override
	public double addNoiseToPixel(double pixel) {
		pixel += gaussianRng.random();
		return pixel;
	}

}
