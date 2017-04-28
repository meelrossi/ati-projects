package filter.diffusion;

public class AnisotropicDiffusionFilterWithLorentzian extends DiffusionFilter {
	
	public AnisotropicDiffusionFilterWithLorentzian(double sigma){
		super(new LorentzianFunction(sigma));
	}

}
