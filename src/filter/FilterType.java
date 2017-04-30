package filter;

import filter.diffusion.AnisotropicDiffusionFilterWithLeclerc;
import filter.diffusion.AnisotropicDiffusionFilterWithLorentzian;
import filter.diffusion.IsotropicDiffusionFilter;

public enum FilterType {
	GAUSSIAN,
	MEAN,
	MEDIAN,
	WEIGHTED_MEDIAN,
	BORDER,
	ANISOTROPIC_DIFFUSION_LECLERC,
	ANISOTROPIC_DIFFUSION_LORENTZIAN,
	ISOTROPIC_DIFUSSION;
	
	public Filter getFilter(double value) {
		switch(this) {
		case GAUSSIAN:
			return new GaussianFilter(value);
		case MEAN:
			return new MeanFilter();
		case MEDIAN:
			return new MedianFilter();
		case WEIGHTED_MEDIAN:
			return new WeightedMedianFilter();
		case BORDER:
			return new BorderFilter();
		case ANISOTROPIC_DIFFUSION_LECLERC:
			return new AnisotropicDiffusionFilterWithLeclerc(value);
		case ANISOTROPIC_DIFFUSION_LORENTZIAN:
			return new AnisotropicDiffusionFilterWithLorentzian(value);
		case ISOTROPIC_DIFUSSION:
			return new IsotropicDiffusionFilter();
		default:
			return null;
		}
	}
}
