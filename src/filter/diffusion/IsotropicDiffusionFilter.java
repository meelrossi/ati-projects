package filter.diffusion;

public class IsotropicDiffusionFilter extends DiffusionFilter {

	public IsotropicDiffusionFilter(){
		super(new OneFunction());
	}

}
