package utils;

public enum ImageOperation {
	PRODUCT, SUM, SUSTRACTION;

	public double apply(double val1, double val2) {
		switch (this) {
		case PRODUCT:
			return val1 * val2;
		case SUM:
			return val1 + val2;
		case SUSTRACTION:
			return val1 - val2;
		default:
			return 0;
		}
	}

}
