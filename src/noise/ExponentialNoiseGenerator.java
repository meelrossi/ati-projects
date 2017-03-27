package noise;

import random_number_generator.ExponentialRandomNumberGenerator;

public class ExponentialNoiseGenerator extends NoiseGenerator {

	private final ExponentialRandomNumberGenerator exponentialRng;
	
	public ExponentialNoiseGenerator(double lambda) {
		this.exponentialRng = new ExponentialRandomNumberGenerator(lambda);
	}
	
	@Override
	public int addNoiseToPixel(int pixel) {
		pixel += exponentialRng.random();
		if(pixel<0){
			return 0;
		} else if(pixel>255){
			return 255;
		}
		return (int)pixel;
	}

	

}
