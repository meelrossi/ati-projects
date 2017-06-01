package matrix_operations.thresholdings;

import utils.ErrorDispersion;

public class MeanAndStandardDeviationThreashholding {

	private static int MIN_T1 = 80;
	private static int MAX_T2 = 150;
	
	private double[][] matrix;
	private int t1;
	private int t2;
	
	public MeanAndStandardDeviationThreashholding(double[][] matrix){
		this.matrix = matrix;
		calculateThreashholds();
	}

	private void calculateThreashholds() {
		double[] values = new double[matrix.length * matrix[0].length];
		for(int i=0 ; i < matrix.length; i++){
			for(int j=0 ; j < matrix[0].length ; j++){
				values[i+j*matrix.length] = matrix[i][j];
			}
		}
		ErrorDispersion statistics = new ErrorDispersion(values);
		double mean = statistics.getMean();
		double deviation = statistics.getStdDev();
		System.out.println(mean);
		System.out.println(deviation);
		t1 = (int) ((mean-deviation) > MIN_T1 ? (mean-deviation) : MIN_T1);
		t2 = (int) ((mean+deviation) < MAX_T2 ? (mean+deviation) : MAX_T2);
	}

	public int getT1() {
		return t1;
	}

	public int getT2() {
		return t2;
	}
	
	
	
	
}
