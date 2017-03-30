package noise;

public enum NoiseType {
	EXPONENTIAL,
	GAUSSIAN,
	RAYLEIGH,
	SALT_AND_PEPPER;
	
	public NoiseGenerator getNoiseGenerator(double val1, double val2) {
		switch (this) {
		case EXPONENTIAL:
			return new ExponentialNoiseGenerator(val1);
		case GAUSSIAN:
			return new GaussianNoiseGenerator(val1, val2);
		case RAYLEIGH:
			return new RayleighNoiseGenerator(val1);
		case SALT_AND_PEPPER:
			return new SaltAndPepperNoiseGenerator(val1, val2);
		default:
			return null;
		}
	}
}
