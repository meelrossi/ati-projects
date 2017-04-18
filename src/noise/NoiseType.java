package noise;

public enum NoiseType {
	EXPONENTIAL("lambda", ""),
	GAUSSIAN ("mean", "standard deviation"),
	RAYLEIGH ("epsilon", ""),
	SALT_AND_PEPPER ("p0", "p1");
	
	private String placeholder1;
	private String placeholder2;
	
	private NoiseType(String placeholder1, String placeholder2) {
		this.placeholder1 = placeholder1;
		this.placeholder2 = placeholder2;
	}
	
	public String getPlaceholder1() {
		return this.placeholder1;
	}
	
	public String getPlaceholder2() {
		return this.placeholder2;
	}

	public NoiseGenerator getNoiseGenerator(Double val1, Double val2, Double percentageAffectedByNoise) {
		switch (this) {
		case EXPONENTIAL:
			return new ExponentialNoiseGenerator(val1,percentageAffectedByNoise);
		case GAUSSIAN:
			return new GaussianNoiseGenerator(val1, val2, percentageAffectedByNoise);
		case RAYLEIGH:
			return new RayleighNoiseGenerator(val1, percentageAffectedByNoise);
		case SALT_AND_PEPPER:
			return new SaltAndPepperNoiseGenerator(val1, val2, percentageAffectedByNoise);
		default:
			return null;
		}
	}
}
