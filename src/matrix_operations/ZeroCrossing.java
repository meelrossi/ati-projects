package matrix_operations;

public class ZeroCrossing {

	public static double[][] process(double[][] matrix, double threashold){
		double[][] result = new double[matrix.length][matrix[0].length];
		double lastNonZeroValue = 0;
		for(int i = 0; i < result.length; i++){
			for(int j = 0; j< result[0].length; j++){
				if(lastNonZeroValue != 0 && matrix[i][j] != 0){
					if(lastNonZeroValue*matrix[i][j] < 0){
						if(Math.abs(lastNonZeroValue - matrix[i][j]) >= threashold){
							result[i][j] = 255;								
						}
					}
				}
				if(matrix[i][j] != 0){
					lastNonZeroValue = matrix[i][j];
				}				 
			}
			lastNonZeroValue = 0;
		}
		
		for(int j = 0; j < result[0].length; j++){
			for(int i = 0; i< result.length; i++){
				if(lastNonZeroValue != 0 && matrix[i][j] != 0){
					if(lastNonZeroValue*matrix[i][j] < 0){
						if(Math.abs(lastNonZeroValue - matrix[i][j]) >= threashold){
							result[i][j] = 255;								
						}
					}
				}
				if(matrix[i][j] != 0){
					lastNonZeroValue = matrix[i][j];
				}
			}
			lastNonZeroValue = 0;
		}
		
		return result;
	}
	
	public static double[][] process(double[][] matrix){
		return process(matrix, 0);
	}
	
	
}
