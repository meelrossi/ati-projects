package matrix_operations;

public class Normalizationer {

	public static double[][] linearlyNormalize(double[][] matrix){
		double min = Integer.MAX_VALUE;
		double max = 0;
		double[][] result = new double[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				
				double n = matrix[i][j];
				min = Math.min(n, min);
				max = Math.max(n, max);
			}
		}
		if(min == max){
			min--;
			max++;
		}

		for (int k = 0; k < matrix.length; k++) {
			for (int r = 0; r < matrix[0].length; r++) {
				result[k][r] = (255 * matrix[k][r]) / (max - min) - (255 * min) / (max - min);
			}
		}
		return result;
	}
	
}
