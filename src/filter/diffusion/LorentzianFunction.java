package filter.diffusion;

public class LorentzianFunction implements Function {

	private double sigma;

	public LorentzianFunction(double sigma) {
		this.sigma = sigma;
	}

	@Override
	public double apply(double number) {
		return 1.0 / (Math.pow(number / sigma, 2) + 1);
	}

}
