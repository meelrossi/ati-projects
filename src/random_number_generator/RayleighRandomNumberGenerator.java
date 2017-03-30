package random_number_generator;

public class RayleighRandomNumberGenerator implements RandomNumberGenerator {

	private final GaussianRandomNumberGenerator gaussianRng;
	
	public RayleighRandomNumberGenerator(double epsilon) {
		this.gaussianRng = new GaussianRandomNumberGenerator(0, Math.pow(epsilon, 2));
	}
	
	@Override
	public double random() {
		double x = gaussianRng.random();
		double y = gaussianRng.random();
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y,2));
	}

}
