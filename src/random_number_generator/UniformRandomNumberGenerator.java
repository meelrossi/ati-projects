package random_number_generator;

import java.util.Random;

public class UniformRandomNumberGenerator implements RandomNumberGenerator {

	private final Random rng;
	
	public UniformRandomNumberGenerator() {
		this.rng = new Random();
	}
	
	@Override
	public double random() {
		return rng.nextDouble();
	}

}
