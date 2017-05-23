package matrix_operations.thresholdings;

import utils.ImageManager;

public class OtsuMethodThresholding {

	public static int getThreshold(double[][] matrix){

		int width = matrix.length;
		int height = matrix[0].length;
		
		double[] histogram = ImageManager.getHistogramData(matrix, width, height);
		double[] acumulativeHistogram = ImageManager.getAcumulativeHistogramData(matrix, width, height);
		double[] m = ImageManager.getAcumulativeMean(matrix, width, height);
		
		int currentThreshold = 0;
		double currentMaxVariance = 0;
		
		for(int i=1; i<=255; i++){
			double newVariance = calculateVariance(i, histogram,acumulativeHistogram, m); 
			if(newVariance > currentMaxVariance){
				currentMaxVariance = newVariance;
				currentThreshold = i;
			}
		}
		System.out.println("Threshold otsu: " + currentThreshold);
		return currentThreshold;
	}

	private static double calculateVariance(int i, double[] histogram, double[] acumulativeHistogram, double[] m) {
		return Math.pow((m[255]*acumulativeHistogram[i] - m[i]), 2)/(acumulativeHistogram[i]*(1-acumulativeHistogram[i]));
	}
	
}
