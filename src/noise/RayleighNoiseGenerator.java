package noise;

import random_number_generator.RayleighRandomNumberGenerator;

public class RayleighNoiseGenerator extends NoiseGenerator {

	private final RayleighRandomNumberGenerator rayleighRng;
	
	public RayleighNoiseGenerator(double epsilon){
		this.rayleighRng = new RayleighRandomNumberGenerator(epsilon);
	}

	@Override
	public int addNoiseToPixel(int pixel) {
		pixel *= rayleighRng.random();
		if(pixel>255){
			return 255;
		}
		return pixel;
	}

}
