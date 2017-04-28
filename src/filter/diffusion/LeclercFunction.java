package filter.diffusion;

public class LeclercFunction implements Function {

	private double sigma;
	
	public LeclercFunction(double sigma){
		this.sigma = sigma;
	}
	
	@Override
	public double apply(double number) {
		return Math.exp(-(Math.pow(number, 2))/Math.pow(sigma, 2));
	}

}
