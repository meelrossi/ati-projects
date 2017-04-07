package noise;

import random_number_generator.ExponentialRandomNumberGenerator;

public class ExponentialNoiseGenerator extends NoiseGenerator {

	private final ExponentialRandomNumberGenerator exponentialRng;
	
	public ExponentialNoiseGenerator(double lambda) {
		this.exponentialRng = new ExponentialRandomNumberGenerator(lambda);
	}
	
	@Override
	public double addNoiseToPixel(double pixel) {
		pixel *= exponentialRng.random();
		return pixel;
	}

	

}
