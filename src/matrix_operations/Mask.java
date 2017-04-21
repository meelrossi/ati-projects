package matrix_operations;

public class Mask {

	private double[][] mask;
	private double default_value = 0;
	private int mx;
	private int my;
	
	
	public Mask(double[][] mask){
		this.mask = mask;
		this.mx = (mask.length-1)/2;
		this.my = (mask[0].length-1)/2;
	}
	
	public Mask(double[][] mask, int default_value){
		this.mask = mask;
		this.mx = (mask.length-1)/2;
		this.my = (mask[0].length-1)/2;
		this.default_value = default_value;
	}
	
	public double[][] apply(double[][] matrix){
		double[][] result = new double[matrix.length][matrix[0].length];
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				if(maskDoesNotFit(matrix, i, j)){
					result[i][j] = default_value;
				} else {
					result[i][j] = applyMaskToPoint(matrix,i,j);
				}
			}
		}
		return result;
	}

	private int applyMaskToPoint(double[][] matrix, int i, int j) {
		int sum = 0;
		for(int x = -mx; x <= mx; x++){
			for(int y = -my; y <= my; y++){
				sum += matrix[i+x][j+y] * mask[x+mx][y+my];
			}
		}
		return sum;
	}

	private boolean maskDoesNotFit(double[][] matrix, int i, int j) {
		int minimunX = mx;
		int minimunY = my;
		return i<minimunX || j<minimunY || i>=matrix.length-minimunX-1 || j>=matrix[0].length-minimunY-1;
	}
	
}
