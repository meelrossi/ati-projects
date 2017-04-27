package matrix_operations.point_to_point;

public abstract class PointToPointOperation {

	public double[][] apply(double[][] matrix1, double[][] matrix2) {
		double[][] result = new double[matrix1.length][matrix1[0].length];
		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix1[0].length; j++) {
				result[i][j] = pointToPoint(matrix1[i][j], matrix2[i][j]);
			}
		}

		return result;
	}

	public abstract double pointToPoint(double value1, double value2);

}
