package filter.diffusion;

public class AnisotropicDiffusionFilterWithLeclerc extends DiffusionFilter {

	public AnisotropicDiffusionFilterWithLeclerc(double sigma){
		super(new LeclercFunction(sigma));
	}
	
}
