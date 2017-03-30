package filter;

public enum FilterType {
	GAUSSIAN,
	MEAN,
	MEDIAN,
	WEIGHTED_MEDIAN;
	
	public Filter getFilter() {
		switch(this) {
		case GAUSSIAN:
			return new GaussianFilter();
		case MEAN:
			return new MeanFilter();
		case MEDIAN:
			return new MedianFilter();
		case WEIGHTED_MEDIAN:
			return new WeightedMedianFilter();
		default:
			return null;
		}
	}
}
