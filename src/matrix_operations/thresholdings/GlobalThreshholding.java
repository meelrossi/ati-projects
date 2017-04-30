package matrix_operations.thresholdings;

import java.util.LinkedList;
import java.util.List;

public class GlobalThreshholding {

	private static int INITIAL_VALUE = 127;

	public static int getThreshold(double[][] matrix) {
		return getThreshold(matrix, 1);
	}

	public static int getThreshold(double[][] matrix, int maxDeltaT) {

		int deltaT = 257;
		int T = INITIAL_VALUE;

		while (deltaT > maxDeltaT) {
			List<Integer> g1 = getAllValuesAboveOrEqualToX(T, matrix);
			List<Integer> g2 = getAllValuesBelowX(T, matrix);

			int nG1 = g1.size();
			int nG2 = g2.size();

			int sumG1 = sum(g1);
			int sumG2 = sum(g2);

			double m1 = 1 / ((double) nG1) * sumG1;
			double m2 = 1 / ((double) nG2) * sumG2;

			int newT = (int) (0.5 * (m1 + m2));
			deltaT = Math.abs(newT - T);
			T = newT;
		}

		return T;
	}

	private static int sum(List<Integer> numbers) {
		int sum = 0;
		for (Integer i : numbers) {
			sum += i;
		}
		return sum;
	}

	private static List<Integer> getAllValuesAboveOrEqualToX(int x, double[][] matrix) {
		List<Integer> result = new LinkedList<>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] >= x) {
					result.add((int) matrix[i][j]);
				}
			}
		}
		return result;
	}

	private static List<Integer> getAllValuesBelowX(int x, double[][] matrix) {
		List<Integer> result = new LinkedList<>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] < x) {
					result.add((int) matrix[i][j]);
				}
			}
		}
		return result;
	}

}
