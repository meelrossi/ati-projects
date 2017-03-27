package random_number_generator;

import java.util.Random;

public class GaussianRandomNumberGenerator implements RandomNumberGenerator {

	private final double mean;
	private final double standardDeviation;
	private final Random rng;
	
	public GaussianRandomNumberGenerator(double mean, double standardDeviation) {
		this.mean = mean;
		this.standardDeviation = standardDeviation;
		this.rng = new Random();
	}
	
	@Override
	public double random(){
		return rng.nextGaussian()*standardDeviation+mean;
	}
	

}
