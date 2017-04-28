package matrix_operations.masks;

public class LoGMask extends Mask{
	
	public LoGMask(int m, double sigma){
		super(getMaskMatrix(m,sigma));
	}
	
	private static double[][] getMaskMatrix(int m, double sigma){
		int size = 2*m+1;
		double[][] maskMatrix = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int x = (size - 1) / 2 - i;
				int y = (size - 1) / 2 - j;
				double d = (x * x + y * y) / (sigma * sigma);
				double exp = Math.exp(-d / 2.0);
				double c = -1.0 / (Math.sqrt(2 * Math.PI) * Math.pow(sigma, 3));
				double logValue = c * (2 - d) * exp;
				maskMatrix[i][j] = logValue;
			}
		}

		return maskMatrix;
	}

}
