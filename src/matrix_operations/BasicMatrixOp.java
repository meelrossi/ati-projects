package matrix_operations;

public class BasicMatrixOp {

	public static double[][] scalarMult(double[][] matrix, double scalar){
		double[][] result = new double[matrix.length][matrix[0].length];
		
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				result[i][j] = matrix[i][j] * scalar;
			}
		}
		return result;
	}
	
	
}
