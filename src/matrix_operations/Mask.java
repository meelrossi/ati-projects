package matrix_operations;

public class Mask {

	private int[][] mask;
	private int default_value = 0;
	private int mx;
	private int my;
	
	
	public Mask(int[][] mask){
		this.mask = mask;
		this.mx = (mask[0].length-1)/2;
		this.my = (mask.length-1)/2;
	}
	
	public Mask(int[][] mask, int default_value){
		this.mask = mask;
		this.default_value = default_value;
	}
	
	public int[][] apply(int[][] matrix){
		int[][] result = new int[matrix[0].length][matrix.length];
		
		for(int i=0; i<matrix[0].length; i++){
			for(int j=0; j<matrix.length; j++){
				if(maskDoesNotFit(matrix, i, j)){
					result[i][j] = default_value;
				} else {
					result[i][j] = applyMaskToPoint(matrix,i,j);
				}
			}
		}
		return result;
	}

	private int applyMaskToPoint(int[][] matrix, int i, int j) {
		int sum = 0;
		for(int x = -mx; x <= mx; x++){
			for(int y = -my; y <= my; y++){
				sum += matrix[i+x][j+y] * mask[x+mx][y+my];
			}
		}
		return sum;
	}

	private boolean maskDoesNotFit(int[][] matrix, int i, int j) {
		int minimunX = (mask[0].length-1)/2;
		int minimunY = (mask.length-1)/2;
		return i<minimunX || j<minimunY || i>=matrix[0].length-minimunX || j>=matrix.length-minimunY;
	}
	
}
