package random_number_generator;

import java.util.Random;

public class ExponentialRandomNumberGenerator implements RandomNumberGenerator {

	private final double lambda;
	private final Random rng;
	
	public ExponentialRandomNumberGenerator(double lambda) {
		this.lambda = lambda;
		this.rng = new Random();
	}
	
	
	@Override
	public double random() {
		return (-1/lambda)*Math.log(rng.nextDouble());
	}

}
