package noise;

import random_number_generator.UniformRandomNumberGenerator;

public class SaltAndPepperNoiseGenerator extends NoiseGenerator {

	private final UniformRandomNumberGenerator uniformRng;
	private final double p1;
	private final double p2;

	public SaltAndPepperNoiseGenerator(double p1, double p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.uniformRng = new UniformRandomNumberGenerator();
	}

	@Override
	public double addNoiseToPixel(double pixel) {
		double p = uniformRng.random();
		System.out.println(p);
		if (p1 > p) {
			return 0;
		} else if (p2 < p) {
			return 255;
		} else {
			return pixel;
		}
	}

}
