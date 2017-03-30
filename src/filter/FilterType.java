package filter;

public enum FilterType {
	GAUSSIAN,
	MEAN,
	MEDIAN,
	WEIGHTED_MEDIAN,
	BORDER;
	
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
		default:
			return null;
		}
	}
}
