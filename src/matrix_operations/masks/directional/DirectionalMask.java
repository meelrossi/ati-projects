package matrix_operations.masks.directional;

import matrix_operations.masks.Mask;

public class DirectionalMask {

	private Mask eMask;
	private Mask seMask;
	private Mask sMask;
	private Mask swMask;
	
	public DirectionalMask(double[][] maskMatrix){
		eMask = new Mask(rotate(maskMatrix, Direction.E.getRotationTimes()));
		seMask = new Mask(rotate(maskMatrix, Direction.SE.getRotationTimes()));
		sMask = new Mask(rotate(maskMatrix, Direction.S.getRotationTimes()));
		swMask = new Mask(rotate(maskMatrix, Direction.SW.getRotationTimes()));
	}
	
	public double[][] apply(double[][] matrix){
		double[][] result = new double[matrix.length][matrix[0].length];
		
		double[][] eMatrix = eMask.apply(matrix);
		double[][] seMatrix = seMask.apply(matrix);
		double[][] sMatrix = sMask.apply(matrix);
		double[][] swMatrix = swMask.apply(matrix);

		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				result[i][j] = getMaskValue(eMatrix[i][j], seMatrix[i][j], sMatrix[i][j], swMatrix[i][j]);
			}
		}
		return result;
	}

	private double getMaskValue(double v1, double v2, double v3, double v4) {
		double max = v1;
		if(v2>max){
			max = v2;
		}
		if(v3>max){
			max = v3;
		}
		if(v4>max){
			max = v4;
		}
		return max;
	}

	private static double[][] rotate(double[][] matrix, int times) {
		double[][] result = copy(matrix);
		for (int i = 0; i < times; i++) {
			rotate(result);
		}
		return result;
	}

	private static double[][] copy(double[][] matrix) {
		double[][] result = new double[3][3];
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				result[i][j] = matrix[i][j];
			}
		}
		
		return result;
	}



	// matrix needs to be a 3x3 matrix
	private static void rotate(double[][] matrix) {
		double aux = matrix[0][0];
		matrix[0][0] = matrix[1][0];
		matrix[1][0] = matrix[2][0];
		matrix[2][0] = matrix[2][1];
		matrix[2][1] = matrix[2][2];
		matrix[2][2] = matrix[1][2];
		matrix[1][2] = matrix[0][2];
		matrix[0][2] = matrix[0][1];
		matrix[0][1] = aux;
	}
	
	
}
